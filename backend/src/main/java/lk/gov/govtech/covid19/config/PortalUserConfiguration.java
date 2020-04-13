package lk.gov.govtech.covid19.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Configuration
@ConfigurationProperties(prefix = "spring.portal")
@Data
@Component
public class PortalUserConfiguration {

    private ArrayList<String[]> userCredentials;

    public ArrayList<String[]> getUserCredentials() {
        return userCredentials;
    }

    public void setUserCredentials(ArrayList<String[]> userCredentials) {
        this.userCredentials = userCredentials;
    }
}
