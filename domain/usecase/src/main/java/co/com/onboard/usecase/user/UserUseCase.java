package co.com.onboard.usecase.user;

import co.com.onboard.model.user.User;
import co.com.onboard.model.user.gateways.UserRepository;
import co.com.onboard.usecase.user.exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class UserUseCase {

    private final UserRepository userRepository;

    public Mono<User> saveUser(Integer id) {
        return null;
    }

    public Mono<User> findById(Integer id) {
        return userRepository.findById(id)
                .switchIfEmpty(Mono.error(new UserNotFoundException("User not found")));
    }

    public Flux<User> findAllUsers() {
        return userRepository.findAllUsers();
    }

    public Flux<User> findUsersByName(String name) {
        return null;
    }
}
