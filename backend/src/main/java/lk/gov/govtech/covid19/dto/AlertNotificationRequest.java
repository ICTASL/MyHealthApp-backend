package lk.gov.govtech.covid19.dto;

import lombok.Data;

@Data
public class AlertNotificationRequest {
    private String title;
    private String subtitle;
    private String source;
    private String messageEn;
    private String messageSi;
    private String messageTa;
}
