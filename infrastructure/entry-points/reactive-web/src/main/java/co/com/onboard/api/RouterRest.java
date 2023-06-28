package co.com.onboard.api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class RouterRest {
    @Bean
    public RouterFunction<ServerResponse> routerFunction(Handler handler) {
        return route(GET("/api/users"), handler::findAllUsersRegistered)
                .andRoute(GET("/api/user/id/{id}"), handler::findUserById)
                .andRoute(GET("/api/user/name/{name}"), handler::findUserByItsFirstName)
                .andRoute(POST("/api/user/{id}"), handler::saveUser);
//                .and(route(GET("/api/otherusercase/path"), handler::listenGETOtherUseCase));
    }
}
