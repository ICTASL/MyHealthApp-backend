package lk.gov.govtech.covid19.dto;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class ErrorResponse {
    private HttpStatus status;
    private String error;
    private String message;
}
