package lk.gov.govtech.covid19.dto;

import java.util.List;

import lombok.Data;

/**
 * Represents the TE attributes information.
 */
@Data
public class TrackedEntityAttributesResponse {
    List<IdDisplayAttribute> trackedEntityAttributes;
}
