package co.com.onboard.consumer;


import co.com.onboard.model.user.gateways.ReqresRepository;
import co.com.onboard.model.user.User;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class RestConsumer implements ReqresRepository {

    private final WebClient client;
    private final Environment environment;

    @Override
    public Mono<User> findById(Integer id) {

        String reqresApiUrl = environment.getProperty("api.reqres-url");

        return client.get()
                .uri(reqresApiUrl + "/api/users/{id}", id)
                .retrieve()
                // Deserialize the HTTP response into a UserResponse object
                .bodyToMono(UserResponse.class)
                .map(response -> {
                    UserResponse.UserData userData = response.getData();
                    return User.builder()
                            .id(userData.getId())
                            .firstName(userData.getFirst_name())
                            .lastName(userData.getLast_name())
                            .email(userData.getEmail())
                            .build();
                });
    }
}
