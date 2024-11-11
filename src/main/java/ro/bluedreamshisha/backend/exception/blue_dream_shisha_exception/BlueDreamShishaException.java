package ro.bluedreamshisha.backend.exception.blue_dream_shisha_exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BlueDreamShishaException extends Exception {

    private final BlueDreamShishaErrorResponse response;

    public BlueDreamShishaException(String errorCode, HttpStatus status) {
        this.response = new BlueDreamShishaErrorResponse(errorCode, status);
    }

    public BlueDreamShishaException(String message, String errorCode, HttpStatus status) {
        super(message);
        this.response = new BlueDreamShishaErrorResponse(message, errorCode, status);
    }

    public BlueDreamShishaException(String errorCode, HttpStatus status, Throwable cause) {
        super(cause);
        this.response = new BlueDreamShishaErrorResponse(errorCode, status);
    }

    public BlueDreamShishaException(String message, String errorCode, HttpStatus status, Throwable cause) {
        super(message, cause);
        this.response = new BlueDreamShishaErrorResponse(message, errorCode, status);
    }
}
