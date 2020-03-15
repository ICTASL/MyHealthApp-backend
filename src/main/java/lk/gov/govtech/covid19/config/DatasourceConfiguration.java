package lk.gov.govtech.covid19.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@ConfigurationProperties(prefix = "spring.datasource")
@Data
@Component
public class DatasourceConfiguration {
    private String url;
    private String username;
    private String password;
    private String driverClass;
}
