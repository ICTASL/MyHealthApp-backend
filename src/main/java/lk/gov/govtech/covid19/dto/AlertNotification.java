package lk.gov.govtech.covid19.dto;

import lombok.Data;

@Data
public class AlertNotification {
    private String id;
    private String source;
    private Title title;
    private Message message;
    private String createdTime;

    @Data
    public static class Title {
        private String english;
        private String sinhala;
        private String tamil;
    }

    @Data
    public static class Message {
        private String english;
        private String sinhala;
        private String tamil;
    }
}
