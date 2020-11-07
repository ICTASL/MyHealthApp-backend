package lk.gov.govtech.covid19.dto;

import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.*;

@Data
public class AlertNotificationRequest {
    @NotBlank @Size(max=45)
    private String source;
    @NotNull @Valid
    private Title title;
    @NotNull @Valid
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
        @Size(max=2500)
        private String sinhala;
        @Size(max=2500)
        private String tamil;
    }
}
