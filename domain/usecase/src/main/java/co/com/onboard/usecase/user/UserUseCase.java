package co.com.onboard.usecase.user;

import co.com.onboard.model.user.gateways.DynamoRepository;
import co.com.onboard.model.user.gateways.ReqresRepository;
import co.com.onboard.model.user.User;
import co.com.onboard.model.user.gateways.SqsRepository;
import co.com.onboard.model.user.gateways.UserRepository;
import co.com.onboard.model.user.exceptions.UserFoundException;
import co.com.onboard.model.user.exceptions.UserNotFoundException;
import lombok.RequiredArgsConstructor;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@RequiredArgsConstructor
public class UserUseCase {

    private final UserRepository userRepository;
    private final ReqresRepository reqresRepository;
    private final SqsRepository sqsRepository;
    private final DynamoRepository dynamoRepository;

    public Mono<User> saveUser(Integer id) {
        return userRepository.findById(id)
                .flatMap(userFound -> Mono.<User>error(new UserFoundException("User with id "
                                                        + userFound.getId() +  ", already exists. Please provide another id"))
                )
                .switchIfEmpty(
                        reqresRepository.findById(id)
                                .flatMap(Mono::just)
                                .flatMap(userRepository::saveUser)
                                .flatMap(user -> sqsRepository.sendUserToQueue(user).thenReturn(user))
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

    public Flux<User> findAllByName(String name) {
        String lowerCaseName = name.toLowerCase();
        String capitalizedName =
                // Capitalize first letter
                Character.toUpperCase(lowerCaseName.charAt(0))
                        // Concatenate the substring
                + lowerCaseName.substring(1);
        return userRepository.findAllByName(capitalizedName)
                .switchIfEmpty(Mono.error(new UserNotFoundException("Users with name '%s' weren't found".formatted(capitalizedName))));
    }

    public Mono<User> saveDynamoData(User user) {
        return dynamoRepository.save(user).thenReturn(user);
    }
}
