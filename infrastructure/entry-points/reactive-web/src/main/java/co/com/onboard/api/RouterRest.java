package co.com.onboard.api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;

@Configuration
public class RouterRest {
    @Bean
    public static RouterFunction<ServerResponse> routerFunction(Handler handler) {
        return RouterFunctions
                .route(GET("/api/users"), handler::findAllUsersRegistered)
                .andRoute(GET("/api/user/id/{id}"), handler::findUserById)
                .andRoute(GET("/api/user/name/{name}"), handler::findUserByItsFirstName)
                .andRoute(POST("/api/user/{id}"), handler::saveUser);
    }
}
