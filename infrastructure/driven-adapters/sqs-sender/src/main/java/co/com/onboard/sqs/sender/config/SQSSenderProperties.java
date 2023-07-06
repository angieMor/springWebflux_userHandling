package co.com.onboard.sqs.sender.config;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Configuration
@ConfigurationProperties(prefix = "entrypoint.sqs")
public class SQSSenderProperties {
    private String accessKey;
    private String secretKey;
    private String region;
    private String queueUrl;
    private String endpoint;
}
