package lk.gov.govtech.covid19.dto;

import lombok.Data;

@Data
public class AlertNotificationByLangResponse {
    private String id;
    private String title;
    private String source;
    private String message;
    private String createdTime;
}
