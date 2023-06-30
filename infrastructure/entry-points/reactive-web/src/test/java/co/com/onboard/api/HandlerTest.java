package co.com.onboard.api;

import co.com.onboard.model.user.User;
import co.com.onboard.usecase.user.UserUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;


import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(SpringExtension.class)
@WebFluxTest
@ContextConfiguration(classes = {RouterRest.class, Handler.class})
class HandlerTest {

//    @Autowired
//    private ApplicationContext applicationContext;
//    @MockBean
//    private UserUseCase userUseCase;
//    private WebTestClient webTestClient;

//    @InjectMocks
//    private Handler handler;
//    @Mock
//    private UserUseCase userUseCase;
//    private WebTestClient webTestClient;
//    @BeforeEach
//    void setUp() {
////        webTestClient = WebTestClient.bindToRouterFunction(RouterRest.router(handler)).build();
//        RouterFunction<ServerResponse> routerFunction = RouterRest.routerFunction(handler);
//        webTestClient = WebTestClient.bindToRouterFunction(routerFunction).build();
//    }

    @Mock
    private UserUseCase userUseCase;

    @InjectMocks
    private Handler handler;

    private WebTestClient webTestClient;

    @BeforeEach
    void setUp() {
        webTestClient = WebTestClient.bindToRouterFunction(RouterRest.routerFunction(handler)).build();
    }

//    @Test
//    void saveUser() {
//        Integer id = 6;
//        User user = DataTestHandler.obtainUser();
//
//        given(userUseCase.saveUser(id)).willReturn(Mono.just(user));
//
//
//        webTestClient.post()
//                .uri("/api/user/{id}", id)
//                .exchange()
//                .expectStatus().isOk();
////                .expectHeader().contentType(MediaType.APPLICATION_JSON)
////                .expectBody()
////                .jsonPath("$.id").isEqualTo(id);
////                .jsonPath("$.name").isEqualTo("John Doe");
//
//
//        verify(userUseCase).saveUser(id);
//    }

    @Test
    void findAllUsersRegistered() {
    }

    @Test
    void findUserById() {
    }

    @Test
    void findUserByItsFirstName() {
    }
}