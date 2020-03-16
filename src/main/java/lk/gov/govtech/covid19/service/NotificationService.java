package lk.gov.govtech.covid19.service;

import lk.gov.govtech.covid19.dto.AlertNotificationRequest;
import lk.gov.govtech.covid19.repository.CovidRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    @Autowired
    CovidRepository repository;

    public void addAlertNotificaiton(AlertNotificationRequest request) {

        repository.addAlertNotification(request);

    }

}
