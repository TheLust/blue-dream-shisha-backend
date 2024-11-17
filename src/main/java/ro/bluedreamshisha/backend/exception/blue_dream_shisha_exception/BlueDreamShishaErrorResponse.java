package ro.bluedreamshisha.backend.exception.blue_dream_shisha_exception;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Schema(
        name = "ErrorResponse",
        description = "Negative response for an API call"
)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class BlueDreamShishaErrorResponse {

    @Schema(
            description = "The specific error code defined",
            example = "INVALID_STARS_ALIGNMENT"
    )
    private String errorCode;

    @Schema(
            description = "The description of the error",
            example = "The stars are not aligned"
    )
    private String errorDescription;

    @Schema(
            description = "The request http status",
            example = "500"
    )
    private Integer status;

    @Schema(
            description = "The date and time when the error occurred",
            example = "2011-11-11T08:11:11.111Z"
    )
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
