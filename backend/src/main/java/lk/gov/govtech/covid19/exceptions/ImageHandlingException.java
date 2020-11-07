package lk.gov.govtech.covid19.exceptions;

public class ImageHandlingException extends Exception {
    public ImageHandlingException(String message) {
        super(message);
    }

    public ImageHandlingException(String message, Throwable cause) {
        super(message, cause);
    }
}
