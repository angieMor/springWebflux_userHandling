package co.com.onboard.r2dbc;

import co.com.onboard.r2dbc.persistence.UserPersistence;
import org.springframework.data.repository.query.ReactiveQueryByExampleExecutor;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

// TODO: This file is just an example, you should delete or modify it
public interface MyReactiveRepository extends ReactiveCrudRepository<UserPersistence, Integer> {

}
