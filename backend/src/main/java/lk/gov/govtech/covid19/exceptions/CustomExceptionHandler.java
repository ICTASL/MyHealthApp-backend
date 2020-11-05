package lk.gov.govtech.covid19.exceptions;

import lk.gov.govtech.covid19.dto.ErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        String message = "Malformed JSON request";
        return buildResponseEntity(HttpStatus.BAD_REQUEST, message);
    }

    private ResponseEntity<Object> buildResponseEntity(HttpStatus status, String message){
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setStatus(status);
        errorResponse.setMessage(message);
        return new ResponseEntity<>(errorResponse, status);
    }
}
