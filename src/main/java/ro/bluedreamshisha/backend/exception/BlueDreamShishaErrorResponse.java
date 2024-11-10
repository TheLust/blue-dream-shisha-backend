package ro.bluedreamshisha.backend.exception;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class BlueDreamShishaErrorResponse {

    private String message;
    private String errorCode;
    private HttpStatus status;
    private LocalDateTime timestamp;
    private Throwable cause;

    public BlueDreamShishaErrorResponse(String errorCode, HttpStatus status) {
        this.errorCode = errorCode;
        this.status = status;
        this.timestamp = LocalDateTime.now();
    }

    public BlueDreamShishaErrorResponse(String message, String errorCode, HttpStatus status) {
        this(errorCode, status);
        this.message = message;
    }

    public BlueDreamShishaErrorResponse(String errorCode, HttpStatus status, Throwable cause) {
        this.errorCode = errorCode;
        this.status = status;
        this.timestamp = LocalDateTime.now();
        this.cause = cause;
    }

    public BlueDreamShishaErrorResponse(String message, String errorCode, HttpStatus status, Throwable cause) {
        this(errorCode, status, cause);
        this.message = message;
    }
}
