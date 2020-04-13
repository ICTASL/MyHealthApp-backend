package lk.gov.govtech.covid19.dto;

import lombok.Data;

import java.util.List;

@Data
public class EntityInstance {

    private String trackedEntityType;
    private String orgUnit;
    private List<Attribute> attributes;
    private Geometry geometry;
}
