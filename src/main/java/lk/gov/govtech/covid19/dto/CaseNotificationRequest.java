package lk.gov.govtech.covid19.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CaseNotificationRequest {
    private String caseNumber;
    private List<location> locations;
    private String message_en;
    private String message_si;
    private String message_ta;

    public CaseNotificationRequest() {
        locations = new ArrayList<>();
    }
}

@Data
class location {
    private String date;
    private String from;
    private String to;
    private String location;
}