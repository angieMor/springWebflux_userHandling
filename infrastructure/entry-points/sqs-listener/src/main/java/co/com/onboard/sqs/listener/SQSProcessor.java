package co.com.onboard.sqs.listener;

import co.com.onboard.model.user.User;
import co.com.onboard.model.user.gateways.SqsRepository;
import co.com.onboard.sqs.listener.config.SQSProperties;
import co.com.onboard.usecase.user.exceptions.TechnicalException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;

import software.amazon.awssdk.services.sqs.model.SendMessageRequest;
import software.amazon.awssdk.services.sqs.model.SendMessageResponse;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.function.Function;

@Service
@Slf4j
@AllArgsConstructor

public class SQSProcessor implements SqsRepository {

    private final SQSProperties properties;
    private final SqsAsyncClient client;
    private final ObjectMapper objectMapper;

    @Override
    public Mono<String> sendUserToQueue(User user) {
        try {
            return this.send(objectMapper.writeValueAsString(user));
        } catch (Exception e) {
            return Mono.error(new TechnicalException(e));
        }
    }

    private Mono<String> send(String message) {
        return Mono.fromCallable(() -> buildRequest(message))
                .flatMap(request -> Mono.fromFuture(client.sendMessage(request)))
                .doOnNext(p -> System.out.println(message + "Response: " + p.messageId()))
                .onErrorResume(e -> {
                    System.out.println("Error: " + message);
                    return Mono.empty();
                })
                .map(SendMessageResponse::messageId);
    }

    private SendMessageRequest buildRequest(String message) {
        return SendMessageRequest.builder()
                .queueUrl(properties.getQueueUrl())
                .messageBody(message)
                .build();
    }


}
