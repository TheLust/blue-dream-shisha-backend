package ro.bluedreamshisha.backend.exception.blue_dream_shisha_exception;

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

    private String errorCode;
    private String errorDescription;
    private Integer status;
    private LocalDateTime timestamp;

    public BlueDreamShishaErrorResponse(String errorCode, HttpStatus status) {
        this.errorCode = errorCode;
        this.status = status.value();
        this.timestamp = LocalDateTime.now();
    }

    public BlueDreamShishaErrorResponse(String errorDescription, String errorCode, HttpStatus status) {
        this(errorCode, status);
        this.errorDescription = errorDescription;
    }
}
