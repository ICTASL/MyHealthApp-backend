package lk.gov.govtech.covid19.dto;

import lombok.Data;

@Data
public class AlertNotificationResponse {
    private String id;
    private String title;
    private String subtitle;
    private String source;
    private String message;
    private String createdTime;
}
