package lk.gov.govtech.covid19.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * Config class for loading firebase related configs
 */
@Configuration
@ConfigurationProperties(prefix = "firebase")
@Data
@Component
public class FirebaseConfiguration {
    private String configPath;
    private String topic;
}
