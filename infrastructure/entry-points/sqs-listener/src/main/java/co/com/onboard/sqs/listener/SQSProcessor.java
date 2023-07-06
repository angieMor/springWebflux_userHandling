package co.com.onboard.sqs.listener;

import co.com.onboard.model.user.User;
import co.com.onboard.usecase.user.UserUseCase;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.services.sqs.model.Message;

import java.util.function.Function;

@Service
@Slf4j
@AllArgsConstructor

public class SQSProcessor implements Function<Message, Mono<Void>> {

private final UserUseCase userUseCase;
private final ObjectMapper objectMapper;

@Override
public Mono<Void> apply(Message message) {
        log.info("Mensaje recibido con la información {}", message.body());
        try {
            System.out.println("Paso 2");
            User user = objectMapper.readValue(message.body(), User.class);
            System.out.println(user);
            return userUseCase.saveDynamoData(user).then();
        } catch (Exception e) {
            log.error("Se ha presentado un error leyendo el evento de creacion de usuario", e);
            return Mono.error(e);
        }
    }
}
