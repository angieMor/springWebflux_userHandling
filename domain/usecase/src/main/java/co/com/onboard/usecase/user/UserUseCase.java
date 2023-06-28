package co.com.onboard.usecase.user;

import co.com.onboard.model.user.gateways.ReqresRepository;
import co.com.onboard.model.user.User;
import co.com.onboard.model.user.gateways.UserRepository;
import co.com.onboard.usecase.user.exceptions.UserFoundException;
import co.com.onboard.usecase.user.exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RequiredArgsConstructor
public class UserUseCase {

    private final UserRepository userRepository;
    private final ReqresRepository reqresRepository;

    public Mono<User> saveUser(Integer id) {
        return userRepository.findById(id)
                .flatMap(userFound -> Mono.<User>error(new UserFoundException("User with name "
                        + userFound.getFirstName() + " " + userFound.getLastName() + " Already exists")))
                .switchIfEmpty(
                        reqresRepository.findById(id)
                                .flatMap(Mono::just)
                                .flatMap(userRepository::saveUser)
                );
    }

    public Mono<User> findById(Integer id) {
        return userRepository.findById(id)
//                .doOnNext(userPersistence -> System.out.println(userPersistence)) // debug print
                .switchIfEmpty(Mono.error(new UserNotFoundException("User with id '%d' wasn't found".formatted(id))));
    }

    public Flux<User> findAllUsers() {
        return userRepository.findAllUsers();
    }

    public Flux<User> findByName(String name) {
        String lowerCaseName = name.toLowerCase();
        String capitalizedName =
                // Capitalize first letter
                Character.toUpperCase(lowerCaseName.charAt(0))
                        // Concatenate the substring
                + lowerCaseName.substring(1);
        return userRepository.findByName(capitalizedName)
                .switchIfEmpty(Mono.error(new UserNotFoundException("User with name '%s' wasn't found".formatted(capitalizedName))));
    }
}
