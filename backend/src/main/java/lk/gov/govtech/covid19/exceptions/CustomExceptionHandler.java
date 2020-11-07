package lk.gov.govtech.covid19.exceptions;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@ControllerAdvice
public class CustomExceptionHandler {

    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Malformed JSON request")
    @ExceptionHandler(value = {HttpMessageNotReadableException.class})
    public void handleMalformedRequestRelatedExceptions(HttpServletRequest request, Exception e) {
        log.warn("Malformed JSON request");
    }

    @ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Invalid request. Value missing or invalid.")
    @ExceptionHandler(value = {MethodArgumentNotValidException.class, HttpMessageConversionException.class,
                            DataIntegrityViolationException.class, IllegalArgumentException.class})
    public void handleValidationException(HttpServletRequest request, Exception e) {
        log.warn("Invalid request. Value missing or invalid.");
    }

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Error while processing image")
    @ExceptionHandler(value = {ImageHandlingException.class})
    public void handleValidationException(HttpServletRequest request, ImageHandlingException e) {
        log.warn("Error while processing image");
    }

    @ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Resource not found")
    @ExceptionHandler(value = {IndexOutOfBoundsException.class})
    public void handleExceptionsLeadingToNotFound(HttpServletRequest request, IndexOutOfBoundsException e) {
        log.warn("Resource not found");
    }

    @ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Unknown error state of server")
    @ExceptionHandler(value = { Exception.class })
    public void defaultErrorHandler(HttpServletRequest request, Exception e) throws Exception {
        log.error("Exception mapping failure. Exception: {}, message: {}",
                e.getClass().getName(), e.getMessage());
    }
}
