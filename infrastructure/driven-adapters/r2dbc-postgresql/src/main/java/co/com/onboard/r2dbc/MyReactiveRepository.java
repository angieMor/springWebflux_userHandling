package co.com.onboard.r2dbc;

import co.com.onboard.model.user.User;
import co.com.onboard.r2dbc.persistence.UserPersistence;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;



// TODO: This file is just an example, you should delete or modify it
@Repository
public interface MyReactiveRepository extends ReactiveCrudRepository<UserPersistence, Integer> {

    Mono<UserPersistence> findByFirstName(String name);
}
