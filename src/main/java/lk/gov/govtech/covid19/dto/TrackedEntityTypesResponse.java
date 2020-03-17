package lk.gov.govtech.covid19.dto;

import java.util.List;

import lombok.Data;

/**
 * Represents the tracked entity types information.
 */
@Data
public class TrackedEntityTypesResponse {
    List<IdDisplayAttribute> trackedEntityTypes;
}
