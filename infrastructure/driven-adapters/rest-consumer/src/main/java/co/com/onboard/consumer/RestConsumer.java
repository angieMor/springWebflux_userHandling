package co.com.onboard.consumer;


import co.com.onboard.model.reqres.gateways.ReqresRepository;
import co.com.onboard.model.user.User;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Collections;
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
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})
                .map(object -> {
                    Map<String, Object> userData = (Map<String, Object>) object.get("data");
                    return User.builder()
                            .id((Integer) userData.get("id"))
                            .firstName((String) userData.get("first_name"))
                            .lastName((String) userData.get("last_name"))
                            .email((String) userData.get("email"))
                            .build();
                });
    }
}
