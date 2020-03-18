package lk.gov.govtech.covid19.model;

import lombok.Data;
import java.sql.Timestamp;

@Data
public class StatusEntity {
    private int sqlTimestamp;
    private int lk_total_case;
    private int lk_recovered_case;
    private int lk_total_deaths;
    private int lk_total_suspect;
    private Timestamp last_update_time;
}
