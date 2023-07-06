package co.com.onboard.sqs.sender;

import co.com.onboard.model.user.User;
import co.com.onboard.model.user.exceptions.TechnicalException;
import co.com.onboard.model.user.gateways.SqsRepository;
import co.com.onboard.sqs.sender.config.SQSSenderProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;
import software.amazon.awssdk.services.sqs.model.SendMessageResponse;

@Service
@Log4j2
@AllArgsConstructor
public class SQSSender implements SqsRepository {
    private final SQSSenderProperties properties;
    private final SqsAsyncClient client;
    private final ObjectMapper objectMapper;

    @Override
    public Mono<String> sendUserToQueue(User user) {
        try {
            System.out.println("Paso 1");
            return this.send(objectMapper.writeValueAsString(user));
        } catch (Exception e) {
            return Mono.error(new TechnicalException(e));
        }
    }
    public Mono<String> send(String message) {
        return Mono.fromCallable(() -> buildRequest(message))
                .flatMap(request -> Mono.fromFuture(client.sendMessage(request)))
                .doOnNext(response -> log.debug("Message sent {}", response.messageId()))
                .map(SendMessageResponse::messageId);
    }

    private SendMessageRequest buildRequest(String message) {
        return SendMessageRequest.builder()
                .queueUrl(properties.getQueueUrl())
                .messageBody(message)
                .build();
    }
}
