package lk.gov.govtech.covid19.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CaseNotificationRequest {
    private String caseNumber;
    @JsonProperty
    private boolean isLocal;
    private String detectedFrom;
    private List<Location> locations;
    private String message_en;
    private String message_si;
    private String message_ta;

    public CaseNotificationRequest() {
        locations = new ArrayList<>();
    }
}

