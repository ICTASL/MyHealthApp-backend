package lk.gov.govtech.covid19.model;

import lk.gov.govtech.covid19.dto.Location;
import lombok.Data;

import java.util.List;

@Data
public class CaseNotificationEntity {
    private String id;
    private String caseNumber;
    private List<Location> locations;
    private String messageEn;
    private String messageSi;
    private String messageTa;
    private String created;
}
