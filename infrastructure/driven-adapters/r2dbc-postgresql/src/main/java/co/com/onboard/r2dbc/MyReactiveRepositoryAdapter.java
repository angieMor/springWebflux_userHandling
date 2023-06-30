package co.com.onboard.r2dbc;

import co.com.onboard.model.user.User;
import co.com.onboard.model.user.gateways.UserRepository;
import co.com.onboard.r2dbc.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class MyReactiveRepositoryAdapter implements UserRepository {

    private final MyReactiveRepository repository;

    @Override
    public Mono<User> saveUser(User user) {
        return repository.save(UserMapper.toPersistenceEntity(user))
                .map(UserMapper::toUser);
    }

    @Override
    @Cacheable(cacheNames = "userCache", key = "#id")
    public Mono<User> findById(Integer id) {
        return repository.findById(id)
//                .doOnNext(userPersistence -> System.out.println(userPersistence))
                .map(UserMapper::toUser);
    }

    @Override
    @Cacheable("userCache")
    public Flux<User> findAllUsers() {
        return repository.findAll().map(UserMapper::toUser);
    }

    @Override
    @Cacheable(cacheNames = "userCache", key = "#name")
    public Flux<User> findAllByName(String name) {
        return Flux.from(repository.findAllByFirstName(name))
                .map(UserMapper::toUser);
    }

}


