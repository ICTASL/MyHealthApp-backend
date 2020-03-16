package lk.gov.govtech.covid19.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class CaseNotificationResponse {
    private String id;
    private String caseNumber;
    private List<Location> locations;
    private String message;
    private String created;
}
