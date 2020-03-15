package lk.gov.govtech.covid19.service;

import lk.gov.govtech.covid19.dto.NotificationResponse;
import lk.gov.govtech.covid19.model.NotificationEntity;
import lk.gov.govtech.covid19.repository.CovidRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ApplicationService {

    @Autowired
    CovidRepository repository;

    public NotificationResponse getNotification(String messageId, String lang) {

        NotificationEntity notification = repository.getNotificationById(messageId);
        NotificationResponse response = null;

        if (notification != null) {
            response = new NotificationResponse();
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

    public Integer getLastNotificationId(){
        return repository.getLastNotificationId();
    }
}
