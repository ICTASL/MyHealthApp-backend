package lk.gov.govtech.covid19.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class Location {
    private String date;
    private String area;
    private String longitude;
    private String latitude;
}
