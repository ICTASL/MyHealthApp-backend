package lk.gov.govtech.covid19.service;

import lk.gov.govtech.covid19.config.FirebaseConfiguration;
import lk.gov.govtech.covid19.dto.AlertNotification;
import lk.gov.govtech.covid19.dto.CaseNotificationRequest;
import lk.gov.govtech.covid19.dto.PushNotificationRequest;
import lk.gov.govtech.covid19.model.AlertNotificationEntity;
import lk.gov.govtech.covid19.repository.CovidRepository;
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

    public void addAlertNotificaiton(AlertNotification request) {

        int id = repository.addAlertNotification(request);
        Map<String, String> data = new HashMap<>();

            executorService.submit(() -> {
            PushNotificationRequest pushNotificationRequest = new PushNotificationRequest();
            pushNotificationRequest.setTopic(firebaseConfiguration.getTopic());
            pushNotificationRequest.setTitle(request.getSource());
            pushNotificationRequest.setMessage(request.getTitle().getEnglish());

            data.put("type", PUSH_NOTIFICATION_MESSAGE_TYPE_ALERT);
            data.put("id", String.valueOf(id));

            pushNotificationService.sendPushNotificationWithData(data, pushNotificationRequest);
        });

    }

    public boolean updateAlertNotification(String alertId, AlertNotification request) {
        return repository.updateAlertNotification(alertId, request);
    }

    public AlertNotification getAlertNotification(String alertId) {
        AlertNotificationEntity notification = repository.getAlertNotificationById(alertId);
        AlertNotification response = null;

        if (notification != null) {
            response = new AlertNotification();
            response.setId(notification.getId());
            response.setSource(notification.getSource());
            response.setCreatedTime(notification.getCreatedTime());
            alertResponseSetTitle(response, notification);
            alertResponseSetMessage(response, notification);
        }
        return response;
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

    private void alertResponseSetTitle(AlertNotification response, AlertNotificationEntity notification) {
        AlertNotification.Title title = new AlertNotification.Title();
        title.setEnglish(notification.getTitleEn());
        if(notification.getTitleSi() != null) {
            title.setSinhala(notification.getTitleSi());
        } else {
            title.setSinhala("");
        }
        if(notification.getTitleTa() != null) {
            title.setTamil(notification.getTitleTa());
        } else {
            title.setTamil("");
        }
        response.setTitle(title);
    }

    private void alertResponseSetMessage(AlertNotification response, AlertNotificationEntity notification) {
        AlertNotification.Message message = new AlertNotification.Message();
        message.setEnglish(notification.getMessageEn());
        if(notification.getMessageSi() != null) {
            message.setSinhala(notification.getMessageSi());
        } else {
            message.setSinhala("");
        }
        if(notification.getMessageTa() != null) {
            message.setTamil(notification.getMessageTa());
        } else {
            message.setTamil("");
        }
        response.setMessage(message);
    }
}
