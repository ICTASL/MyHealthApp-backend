package lk.gov.govtech.covid19.dto;

import lombok.Data;

@Data
public class UpdateStatusRequest {
    private int lk_total_case;
    private int lk_recovered_case;
    private int lk_total_deaths;
    private int lk_total_suspect;
}
