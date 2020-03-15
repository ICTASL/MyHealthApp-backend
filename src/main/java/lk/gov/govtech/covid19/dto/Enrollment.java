package lk.gov.govtech.covid19.dto;

import lombok.Data;

@Data
public class Enrollment {
    private String trackedEntityInstance;
    private String program;
    private String status;
    private String orgUnit;
    private String enrollmentDate;
    private String incidentDate;
}
