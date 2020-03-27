package lk.gov.govtech.covid19.service;

import lk.gov.govtech.covid19.dto.AlertNotificationResponse;
import lk.gov.govtech.covid19.dto.CaseNotificationResponse;
import lk.gov.govtech.covid19.dto.StatusResponse;
import lk.gov.govtech.covid19.dto.UpdateStatusRequest;
import lk.gov.govtech.covid19.model.AlertNotificationEntity;
import lk.gov.govtech.covid19.model.CaseNotificationEntity;
import lk.gov.govtech.covid19.model.StatusEntity;
import lk.gov.govtech.covid19.repository.CovidRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;

@Service
public class ApplicationService {

    @Autowired
    CovidRepository repository;

    @Cacheable(value = "alerts")
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
                    if (notification.getMessageSi().isEmpty()) {
                        response.setMessage(notification.getMessageEn());
                    } else {
                        response.setMessage(notification.getMessageSi());
                    }
                    break;
                case "ta":
                    if (notification.getMessageTa().isEmpty()) {
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

    @Cacheable(value = "cases")
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
                    if (notification.getMessageSi().isEmpty()) {
                        response.setMessage(notification.getMessageEn());
                    } else {
                        response.setMessage(notification.getMessageSi());
                    }
                    break;
                case "ta":
                    if (notification.getMessageTa().isEmpty()) {
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
            response.setLocal(notification.isLocal());
            response.setDetectedFrom(notification.getDetectedFrom());
            response.setCreated(notification.getCreated());
            response.setLocations(repository.getCaseNotificationLocations(caseId));

        }

    return response;
    }

    public Integer getLastCaseNotificationId(){
        return repository.getLastCaseNotificationId();
    }

    public StatusResponse getStatus() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        StatusEntity status = repository.getStatus();
        StatusResponse response = null;

        if (status != null) {
            response = new StatusResponse();

            response.setLk_total_case(status.getLk_total_case());
            response.setLk_recovered_case(status.getLk_recovered_case());
            response.setLk_total_deaths(status.getLk_total_deaths());
            response.setLk_total_suspect(status.getLk_total_suspect());
            response.setLast_update_time(sdf.format(status.getLast_update_time()));
        }
        return response;
    }

    public void updateStatus(UpdateStatusRequest request) {
        repository.updateStatus(request);

    }
}


