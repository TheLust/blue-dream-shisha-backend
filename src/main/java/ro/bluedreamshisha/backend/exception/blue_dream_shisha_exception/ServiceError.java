package ro.bluedreamshisha.backend.exception.blue_dream_shisha_exception;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Schema(
        name = "Error",
        description = "Negative response for an API call"
)
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ServiceError {

    @Schema(
            description = "The specific error code defined",
            example = "INVALID_STARS_ALIGNMENT"
    )
    public String errorCode;

    @Schema(
            description = "The description of the error",
            example = "The stars are not aligned"
    )
    public String errorDescription;

    @Schema(
            description = "The request http status",
            example = "500"
    )
    public Integer status;

    @Schema(
            description = "The date and time when the error occurred",
            example = "2011-11-11T08:11:11.111Z"
    )
    public LocalDateTime timestamp;
}
