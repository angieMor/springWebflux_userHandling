package co.com.onboard.r2dbc;

import co.com.onboard.r2dbc.persistence.UserPersistence;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;



// TODO: This file is just an example, you should delete or modify it
@Repository
public interface MyReactiveRepository extends ReactiveCrudRepository<UserPersistence, Integer> {

    Flux<UserPersistence> findAllByFirstName(String name);
}
