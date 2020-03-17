package lk.gov.govtech.covid19.dto;

import java.util.List;

import lombok.Data;

/**
 * Represents the organization units attributes information.
 */
@Data
public class OrgUnitAttributesResponse {
    List<IdDisplayAttribute> organisationUnits;
}
