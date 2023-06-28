package co.com.onboard.model.user.gateways;

import co.com.onboard.model.user.User;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserRepository {

    Mono<User> saveUser(User user);
    Mono<User> findById(Integer id);
    Flux<User> findAllUsers();
    Flux<User> findByName(String name);
}
