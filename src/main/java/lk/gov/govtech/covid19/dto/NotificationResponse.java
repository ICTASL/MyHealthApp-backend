package lk.gov.govtech.covid19.dto;

import lk.gov.govtech.covid19.model.NotificationEntity;
import lombok.Data;

@Data
public class NotificationResponse {
    private String id;
    private String title;
    private String subtitle;
    private String source;
    private String message;
    private String createdTime;
}
