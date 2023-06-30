package co.com.onboard.api;

import co.com.onboard.model.user.User;
import co.com.onboard.usecase.user.UserUseCase;
import co.com.onboard.usecase.user.exceptions.UserFoundException;
import co.com.onboard.usecase.user.exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class Handler {


    private final UserUseCase userUseCase;

    public Mono<ServerResponse> saveUser(ServerRequest serverRequest){
        Integer id = Integer.parseInt(serverRequest.pathVariable("id"));

        return userUseCase.saveUser(id)
                .flatMap(userSaved -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(userSaved))
                .onErrorResume(UserFoundException.class, ErrorHandlingUtils::handleUserFoundException);
    }

    public Mono<ServerResponse> findAllUsersRegistered(ServerRequest serverRequest){
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(userUseCase.findAllUsers(), User.class);
    }

    public Mono<ServerResponse> findUserById(ServerRequest serverRequest) {
        Integer id = Integer.parseInt(serverRequest.pathVariable("id"));
        return userUseCase.findById(id)
                .flatMap(user -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(user))
                .onErrorResume(UserNotFoundException.class, ErrorHandlingUtils::handleUserNotFoundException);
    }

    public Mono<ServerResponse> findUserByItsFirstName(ServerRequest serverRequest) {
        String name = serverRequest.pathVariable("name");
        return userUseCase.findAllByName(name)
                .collectList()
                .flatMap(users -> {
                    if (users.isEmpty()) {
                        return Mono.error(new UserNotFoundException(""));
                    } else {
                        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(users);
                    }
                })
                .onErrorResume(UserNotFoundException.class, ex -> {

                    return ServerResponse.status(HttpStatus.NOT_FOUND)
                            .contentType(MediaType.APPLICATION_JSON)
                            .bodyValue(Map.of("error", ex.getMessage()));
                });
    }
}
