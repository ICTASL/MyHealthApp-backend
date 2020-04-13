package lk.gov.govtech.covid19.service;

import lk.gov.govtech.covid19.config.FirebaseConfiguration;
import lk.gov.govtech.covid19.dto.AlertNotificationRequest;
import lk.gov.govtech.covid19.dto.CaseNotificationRequest;
import lk.gov.govtech.covid19.dto.PushNotificationRequest;
import lk.gov.govtech.covid19.repository.CovidRepository;
import lk.gov.govtech.covid19.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static lk.gov.govtech.covid19.util.Constants.PUSH_NOTIFICATION_MESSAGE_TYPE_ALERT;
import static lk.gov.govtech.covid19.util.Constants.PUSH_NOTIFICATION_MESSAGE_TYPE_CASE;

@Service
public class NotificationService {

    @Autowired
    CovidRepository repository;

    @Autowired
    PushNotificationService pushNotificationService;

    ExecutorService executorService;

    @Autowired
    FirebaseConfiguration firebaseConfiguration;

    @PostConstruct
    private void init() {
        executorService = Executors.newFixedThreadPool(5);
    }

    @PreDestroy
    private void shutdown() {
        executorService.shutdown();
    }

    public void addAlertNotificaiton(AlertNotificationRequest request) {

        int id = repository.addAlertNotification(request);
        Map<String, String> data = new HashMap<>();

            executorService.submit(() -> {
            PushNotificationRequest pushNotificationRequest = new PushNotificationRequest();
            pushNotificationRequest.setTopic(firebaseConfiguration.getTopic());
            pushNotificationRequest.setTitle(request.getSource());
            pushNotificationRequest.setMessage(request.getTitle());

            data.put("type", PUSH_NOTIFICATION_MESSAGE_TYPE_ALERT);
            data.put("id", String.valueOf(id));

            pushNotificationService.sendPushNotificationWithData(data, pushNotificationRequest);
        });

    }

    public void addCaseNotification(CaseNotificationRequest request) {
       int id = repository.addCaseNotification(request);

        Map<String, String> data = new HashMap<>();

        executorService.submit(() -> {
            PushNotificationRequest pushNotificationRequest = new PushNotificationRequest();
            pushNotificationRequest.setTopic(firebaseConfiguration.getTopic());

            data.put("type", PUSH_NOTIFICATION_MESSAGE_TYPE_CASE);
            data.put("id", String.valueOf(id));

            pushNotificationService.sendPushNotificationWithData(data, pushNotificationRequest);
        });
    }

}
