package co.com.onboard.api;

import co.com.onboard.usecase.user.exceptions.UserFoundException;
import co.com.onboard.usecase.user.exceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

public class ErrorHandlingUtils {

    public static Mono<ServerResponse> handleUserNotFoundException(UserNotFoundException ex) {
        return ServerResponse.status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(buildErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage()));
    }

    public static Mono<ServerResponse> handleUserFoundException(UserFoundException ex) {
        return ServerResponse.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(buildErrorResponse(HttpStatus.FOUND.value(), ex.getMessage()));
    }

    private static Map<String, Object> buildErrorResponse(int status, String message) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("status", status);
        errorResponse.put("message", message);
        return errorResponse;
    }
}
