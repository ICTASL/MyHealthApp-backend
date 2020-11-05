package lk.gov.govtech.covid19.dto;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class UpdateStatusRequest {
    @NotNull @Min(0) @Max(22000000)
    private Integer lk_total_case;

    @NotNull @Min(0) @Max(22000000)
    private Integer lk_recovered_case;

    @NotNull @Min(0) @Max(22000000)
    private Integer lk_total_deaths;

    @NotNull @Min(0) @Max(22000000)
    private Integer lk_total_suspect;
}
