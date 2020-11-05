package lk.gov.govtech.covid19.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class AlertNotificationRequest {
    @NotBlank @Size(max=45)
    private String source;
    private Title title;
    private Message message;

    @Data
    public static class Title {
        @NotBlank @Size(max=100)
        private String english;
        @Size(max=100)
        private String sinhala;
        @Size(max=100)
        private String tamil;
    }

    @Data
    public static class Message {
        @NotBlank @Size(min=8, max=2500)
        private String english;
        @Size(min=8, max=2500)
        private String sinhala;
        @Size(min=8, max=2500)
        private String tamil;
    }
}
