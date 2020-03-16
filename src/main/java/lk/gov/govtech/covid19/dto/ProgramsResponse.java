package lk.gov.govtech.covid19.dto;

import java.util.List;

import lombok.Data;

/**
 * Represents the programs information.
 */
@Data
public class ProgramsResponse {
    List<IdDisplayAttribute> programs;
}
