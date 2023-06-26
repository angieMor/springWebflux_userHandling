package co.com.onboard.api;

import co.com.onboard.model.user.User;
import co.com.onboard.usecase.user.UserUseCase;
import co.com.onboard.usecase.user.exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class Handler {


    private final UserUseCase userUseCase;

    public Mono<ServerResponse> findAllUsersRegistered(ServerRequest serverRequest){
        return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).body(userUseCase.findAllUsers(), User.class);
    }


    public Mono<ServerResponse> findUserById(ServerRequest request) {
        Integer id = Integer.parseInt(request.pathVariable("id"));
        return userUseCase.findById(id)
                .flatMap(user -> ServerResponse.ok().contentType(MediaType.APPLICATION_JSON).bodyValue(user))
                .onErrorResume(UserNotFoundException.class, ErrorHandlingUtils::handleUserNotFoundException);
    }


    public Mono<ServerResponse> listenGETOtherUseCase(ServerRequest serverRequest) {
        // useCase2.logic();
        return ServerResponse.ok().bodyValue("");
    }

    public Mono<ServerResponse> listenPOSTUseCase(ServerRequest serverRequest) {
        // usecase.logic();
        return ServerResponse.ok().bodyValue("");
    }

}
