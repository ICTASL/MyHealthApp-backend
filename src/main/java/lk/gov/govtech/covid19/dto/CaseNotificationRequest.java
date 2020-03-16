package lk.gov.govtech.covid19.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CaseNotificationRequest {
    private String caseNumber;
    private List<Location> locations;
    private String message_en;
    private String message_si;
    private String message_ta;

    public CaseNotificationRequest() {
        locations = new ArrayList<>();
    }
}

