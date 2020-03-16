package lk.gov.govtech.covid19.service;

import lk.gov.govtech.covid19.dto.AlertNotificationResponse;
import lk.gov.govtech.covid19.dto.CaseNotificationResponse;
import lk.gov.govtech.covid19.model.AlertNotificationEntity;
import lk.gov.govtech.covid19.model.CaseNotificationEntity;
import lk.gov.govtech.covid19.repository.CovidRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApplicationService {

    @Autowired
    CovidRepository repository;

    public AlertNotificationResponse getAlertNotification(String messageId, String lang) {

        AlertNotificationEntity notification = repository.getAlertNotificationById(messageId);
        AlertNotificationResponse response = null;

        if (notification != null) {
            response = new AlertNotificationResponse();
            //assumes that english is always set (default language)
            switch (lang) {
                case "en":
                    response.setMessage(notification.getMessageEn());
                    break;
                case "si":
                    if (notification.getMessageSi() == null) {
                        response.setMessage(notification.getMessageEn());
                    } else {
                        response.setMessage(notification.getMessageSi());
                    }
                    break;
                case "ta":
                    if (notification.getMessageTa() == null) {
                        response.setMessage(notification.getMessageEn());
                    } else {
                        response.setMessage(notification.getMessageTa());
                    }
                    break;
                default:
                    response.setMessage(notification.getMessageEn());
                    break;
            }

            response.setId(notification.getId());
            response.setTitle(notification.getTitle());
            response.setSubtitle(notification.getSubtitle());
            response.setSource(notification.getSource());
            response.setCreatedTime(notification.getCreatedTime());
        }
        return response;
    }

    public Integer getLastAlertNotificationId() {
        return repository.getLastAlertNotificationId();
    }

    public CaseNotificationResponse getCaseNotification(String caseId, String lang) {
        CaseNotificationEntity notification = repository.getCaseNotificationById(caseId);
        CaseNotificationResponse response = null;

        if (notification != null){
             response = new CaseNotificationResponse();

            switch (lang) {
                case "en":
                    response.setMessage(notification.getMessageEn());
                    break;
                case "si":
                    if (notification.getMessageSi() == null) {
                        response.setMessage(notification.getMessageEn());
                    } else {
                        response.setMessage(notification.getMessageSi());
                    }
                    break;
                case "ta":
                    if (notification.getMessageTa() == null) {
                        response.setMessage(notification.getMessageEn());
                    } else {
                        response.setMessage(notification.getMessageTa());
                    }
                    break;
                default:
                    response.setMessage(notification.getMessageEn());
                    break;
            }

            response.setId(notification.getId());
            response.setCaseNumber(notification.getCaseNumber());
            response.setCreated(notification.getCreated());
            response.setLocations(repository.getCaseNotificationLocations(caseId));

        }

    return response;
    }

    public Integer getLastCaseNotificationId(){
        return repository.getLastCaseNotificationId();
    }

}
