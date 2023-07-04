package co.com.onboard.sqs.listener.config;

import co.com.onboard.sqs.listener.helper.SQSListener;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.publisher.Mono;
import software.amazon.awssdk.auth.credentials.*;
import software.amazon.awssdk.metrics.MetricPublisher;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;
import software.amazon.awssdk.services.sqs.model.Message;

import java.net.URI;
import java.util.function.Function;

@Configuration
@ConditionalOnMissingBean(SqsAsyncClient.class)
public class SQSConfig {

    @Bean
    public SqsAsyncClient configSqs(SQSProperties properties, MetricPublisher publisher) {
        return SqsAsyncClient.builder()
                .endpointOverride(resolveEndpoint(properties))
                .region(Region.of(properties.getRegion()))
                .overrideConfiguration(o -> o.addMetricPublisher(publisher))
//                .credentialsProvider(getProviderChain())
                .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(properties.getAccessKey(), properties.getSecretKey())))
                .build();
    }

//    private AwsCredentialsProviderChain getProviderChain() {
//        return AwsCredentialsProviderChain.builder()
//                .addCredentialsProvider(EnvironmentVariableCredentialsProvider.create())
//                .addCredentialsProvider(SystemPropertyCredentialsProvider.create())
//                .addCredentialsProvider(WebIdentityTokenFileCredentialsProvider.create())
//                .addCredentialsProvider(ProfileCredentialsProvider.create())
//                .addCredentialsProvider(ContainerCredentialsProvider.builder().build())
//                .addCredentialsProvider(InstanceProfileCredentialsProvider.create())
//                .build();
//    }

    private URI resolveEndpoint(SQSProperties properties) {
        if (properties.getEndpoint() != null) {
            return URI.create(properties.getEndpoint());
        }
        return null;
    }
}
