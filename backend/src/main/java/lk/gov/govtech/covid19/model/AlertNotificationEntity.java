package lk.gov.govtech.covid19.model;

import lombok.Data;

/**
 * Entity for notifications
 */

@Data
public class AlertNotificationEntity {
    private String id;
    private String source;
    private String titleEn;
    private String titleSi;
    private String titleTa;
    private String messageEn;
    private String messageSi;
    private String messageTa;
    private String createdTime;
}
