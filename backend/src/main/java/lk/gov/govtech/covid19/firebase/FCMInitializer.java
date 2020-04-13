package lk.gov.govtech.covid19.firebase;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import javax.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.IOException;

@Service
@Slf4j
public class FCMInitializer {

    @Value("${firebase.config-path}")
    private String firebaseConfigPath;

    @PostConstruct
    public void initialize() {
        try {
            FirebaseOptions options;
            if (ResourceUtils.getFile(firebaseConfigPath).exists()) {
                log.info("FCM credential loaded from filepath");
                options = new FirebaseOptions.Builder()
                        .setCredentials(GoogleCredentials.fromStream(new FileInputStream(ResourceUtils.getFile(firebaseConfigPath)))).build();

            } else {
                log.info("FCM credential loaded from classpath");
                options = new FirebaseOptions.Builder()
                        .setCredentials(GoogleCredentials.fromStream(new ClassPathResource(firebaseConfigPath).getInputStream())).build();

            }

            log.info(options.toString());
            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
                log.info("Firebase application has been initialized");
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

}
