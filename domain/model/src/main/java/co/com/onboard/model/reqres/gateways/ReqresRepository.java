package co.com.onboard.model.reqres.gateways;

import co.com.onboard.model.reqres.Reqres;
import reactor.core.publisher.Mono;

public interface ReqresRepository {

    Mono<Reqres> findById(Integer id);
}
