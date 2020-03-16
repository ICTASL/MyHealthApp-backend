package lk.gov.govtech.covid19.service;

import lk.gov.govtech.covid19.dto.AlertNotificationRequest;
import lk.gov.govtech.covid19.dto.CaseNotificationRequest;
import lk.gov.govtech.covid19.dto.PushNotificationRequest;
import lk.gov.govtech.covid19.model.PushNotificationEntity;
import lk.gov.govtech.covid19.repository.CovidRepository;
import lk.gov.govtech.covid19.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class NotificationService {

    @Autowired
    CovidRepository repository;

    @Autowired
    PushNotificationService pushNotificationService;

    @Autowired
    JsonUtil jsonUtil;

    ExecutorService executorService;

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

        PushNotificationEntity entity = new PushNotificationEntity();
        entity.setId(id);
        entity.setType("alert");

        executorService.submit(() -> {
            PushNotificationRequest pushNotificationRequest = new PushNotificationRequest();
//            pushNotificationRequest.setTitle("test title");
//            pushNotificationRequest.setMessage(jsonUtil.objectToJson(entity));
            pushNotificationRequest.setTopic("mobile_message");

           // pushNotificationService.sendPushNotificationWithoutData(pushNotificationRequest);
            pushNotificationService.sendPushNotificationWithData(new HashMap<String,String>(),pushNotificationRequest);
        });

    }

    public void addCaseNotification(CaseNotificationRequest request) {
        repository.addCaseNotification(request);
    }

}
