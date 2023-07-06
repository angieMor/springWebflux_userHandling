package co.com.onboard.model.user.gateways;

import co.com.onboard.model.user.User;
import reactor.core.publisher.Mono;

public interface DynamoRepository {
    Mono<Void> save(User user);
}
