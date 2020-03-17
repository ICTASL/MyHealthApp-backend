package lk.gov.govtech.covid19.dto;

import java.util.List;

import lombok.Data;

@Data
public class Event {
    private String trackedEntityInstance;
    private String program;
    private String programStage;
    private String orgUnit;
    private String enrollment;
    private String dueDate;
    private String eventDate;
    private String status;
    private List<DataElement> dataValues;
}
