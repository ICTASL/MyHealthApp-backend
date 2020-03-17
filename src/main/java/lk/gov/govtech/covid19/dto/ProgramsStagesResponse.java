package lk.gov.govtech.covid19.dto;

import java.util.List;

import lombok.Data;

/**
 * Represents the program stages information.
 */
@Data
public class ProgramsStagesResponse {
    List<IdDisplayAttribute> programStages;
}
