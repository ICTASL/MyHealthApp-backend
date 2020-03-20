package lk.gov.govtech.covid19.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * Represents Organization Units
 */
@Data
public class OrganizationUnit {
    public String id;
    public String displayName;
    public Polygon[] geometry;
    public int level;
}

