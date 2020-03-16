package lk.gov.govtech.covid19.model;

import lombok.Data;

/**
 * Entity for notifications
 */

@Data
public class AlertNotificationEntity {
    private String id;
    private String title;
    private String subtitle;
    private String source;
    private String messageEn;
    private String messageSi;
    private String messageTa;
    private String createdTime;
}
