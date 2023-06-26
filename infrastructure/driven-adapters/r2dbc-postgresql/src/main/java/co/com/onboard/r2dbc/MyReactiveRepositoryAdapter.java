package co.com.onboard.r2dbc;

import co.com.onboard.model.user.User;
import co.com.onboard.model.user.gateways.UserRepository;
import co.com.onboard.r2dbc.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class MyReactiveRepositoryAdapter implements UserRepository {

    private final MyReactiveRepository repository;

    @Override
    public Mono<User> saveUser(User user) {
//        return repository.save(user);
        return null;
    }

    @Override
    public Mono<User> findById(Integer id) {
        return null;
    }

    @Override
    public Flux<User> findAllUsers() {
        return repository.findAll().map(UserMapper::toUser);
    }

    @Override
    public Flux<User> findUsersByName(String name) {
        return null;
    }

}


