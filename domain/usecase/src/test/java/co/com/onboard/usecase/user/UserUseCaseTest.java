package co.com.onboard.usecase.user;

import co.com.onboard.model.user.User;
import co.com.onboard.model.user.gateways.ReqresRepository;
import co.com.onboard.model.user.gateways.UserRepository;
import co.com.onboard.usecase.user.exceptions.UserFoundException;

import co.com.onboard.usecase.user.exceptions.UserNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;


import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class UserUseCaseTest {

    @InjectMocks
    UserUseCase userUseCase;

    @Mock
    UserRepository userRepository;

    @Mock
    ReqresRepository reqresRepository;

    @Test
    void saveUser_ThrowUserFoundException() {
        User user = DataTestUserUseCase.obtainUser();

        given(userRepository.findById(anyInt())).willReturn(Mono.just(user));
        given(reqresRepository.findById(anyInt())).willReturn(Mono.empty());

        Mono<User> result = userUseCase.saveUser(user.getId());

        StepVerifier.create(result)
                .expectError(UserFoundException.class)
                .verify();
    }

    @Test
    void saveUser_UserNotFound_savesUserFromExternalApi() {
        User user = DataTestUserUseCase.obtainUser();

        given(userRepository.findById(anyInt())).willReturn(Mono.empty());
        given(reqresRepository.findById(anyInt())).willReturn(Mono.just(user));
        given(userRepository.saveUser(any(User.class))).willReturn(Mono.just(user));

        Mono<User> result = userUseCase.saveUser(user.getId());

        StepVerifier.create(result)
                .expectNext(user)
                .verifyComplete();

    }

    @Test
    void findById_ThrowsUserNotFoundException() {

        given(userRepository.findById(anyInt())).willReturn(Mono.empty());

        Mono<User> result = userUseCase.findById(5);

        StepVerifier.create(result)
                .expectError(UserNotFoundException.class)
                .verify();
    }

    @Test
    void findById() {
        User user = DataTestUserUseCase.obtainUser();

        given(userRepository.findById(anyInt())).willReturn(Mono.just(user));

        Mono<User> result = userUseCase.findById(user.getId());

        StepVerifier.create(result)
                .expectNext(user)
                .verifyComplete();
    }

    @Test
    void findAllUsers() {
        Flux<User> allUsers = DataTestUserUseCase.obtainAllUsers();

        given(userRepository.findAllUsers()).willReturn(allUsers);

        Flux<User> result = userUseCase.findAllUsers();

        StepVerifier.create(result)
                .expectNextCount(allUsers.collectList().blockOptional().orElseThrow().size())
                .verifyComplete();
    }

    @Test
    void findByName_ThrowsUserNotFoundException() {

        given(userRepository.findByName(anyString())).willReturn(Flux.empty());

        Flux<User> result = userUseCase.findByName("Joshua");

        StepVerifier.create(result)
                .expectError(UserNotFoundException.class)
                .verify();

    }

    @Test
    void findByName() {
        Flux<User> usersFoundByName = DataTestUserUseCase.obtainUsersByName();
        String firstNameUsers = usersFoundByName.elementAt(0).block().getFirstName();

        given(userRepository.findByName(anyString())).willReturn(usersFoundByName);

        Flux<User> result = userUseCase.findByName(firstNameUsers);

        StepVerifier.create(result)
                .expectNextCount(usersFoundByName.collectList().blockOptional().orElseThrow().size())
                .verifyComplete();
    }
}