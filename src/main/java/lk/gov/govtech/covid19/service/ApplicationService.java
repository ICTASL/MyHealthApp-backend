package lk.gov.govtech.covid19.service;

import lk.gov.govtech.covid19.dto.AlertNotificationByLangResponse;
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
    public AlertNotificationByLangResponse getAlertNotification(String messageId, String lang) {

        AlertNotificationEntity notification = repository.getAlertNotificationById(messageId);
        AlertNotificationByLangResponse response = null;

        if (notification != null) {
            response = new AlertNotificationByLangResponse();
            //assumes that english is always set (default language)
            switch (lang) {
                case "en":
                    alertResponseSetEnglishTitle(response, notification);
                    alertResponseSetEnglishMessage(response, notification);
                    break;
                case "si":
                    alertResponseSetSinhalaTitleOrElseEnglish(response, notification);
                    alertResponseSetSinhalaMessageOrElseEnglish(response, notification);
                    break;
                case "ta":
                    alertResponseSetTamilTitleOrElseEnglish(response, notification);
                    alertResponseSetTamilMessageOrElseEnglish(response, notification);
                    break;
                default:
                    alertResponseSetEnglishTitle(response, notification);
                    alertResponseSetEnglishMessage(response, notification);
                    break;
            }

            response.setId(notification.getId());
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
            response.setLocations(repository.getCaseNotificationLocations(notification.getId()));

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

    /*
     *  Set alert response title
     * */
    private void alertResponseSetEnglishTitle(AlertNotificationByLangResponse response, AlertNotificationEntity notification) {
        response.setTitle(notification.getTitleEn());
    }
    private void alertResponseSetSinhalaTitleOrElseEnglish(AlertNotificationByLangResponse response, AlertNotificationEntity notification) {
        if (notification.getTitleSi() == null || notification.getTitleSi().isEmpty()) {
            alertResponseSetEnglishTitle(response, notification);
        } else {
            response.setTitle(notification.getTitleSi());
        }
    }
    private void alertResponseSetTamilTitleOrElseEnglish(AlertNotificationByLangResponse response, AlertNotificationEntity notification) {
        if (notification.getTitleTa() == null || notification.getTitleTa().isEmpty()) {
            alertResponseSetEnglishTitle(response, notification);
        } else {
            response.setTitle(notification.getTitleTa());
        }
    }

    /*
    *  Set alert response message
    * */
    private void alertResponseSetEnglishMessage(AlertNotificationByLangResponse response, AlertNotificationEntity notification) {
        response.setMessage(notification.getMessageEn());
    }
    private void alertResponseSetSinhalaMessageOrElseEnglish(AlertNotificationByLangResponse response, AlertNotificationEntity notification) {
        if (notification.getMessageSi() == null || notification.getMessageSi().isEmpty()) {
            alertResponseSetEnglishMessage(response, notification);
        } else {
            response.setMessage(notification.getMessageSi());
        }
    }
    private void alertResponseSetTamilMessageOrElseEnglish(AlertNotificationByLangResponse response, AlertNotificationEntity notification) {
        if (notification.getMessageTa() == null || notification.getMessageTa().isEmpty()) {
            alertResponseSetEnglishMessage(response, notification);
        } else {
            response.setMessage(notification.getMessageTa());
        }
    }
}


