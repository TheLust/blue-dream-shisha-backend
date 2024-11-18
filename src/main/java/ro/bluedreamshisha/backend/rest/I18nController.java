package ro.bluedreamshisha.backend.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.bluedreamshisha.backend.constant.ErrorCode;
import ro.bluedreamshisha.backend.constant.SwaggerDescription;
import ro.bluedreamshisha.backend.exception.blue_dream_shisha_exception.BlueDreamShishaException;
import ro.bluedreamshisha.backend.exception.blue_dream_shisha_exception.BlueDreamShishaErrorResponse;
import ro.bluedreamshisha.backend.facade.I18nFacade;

import java.io.IOException;

@RestController
@RequestMapping("${api.url.base}/i18n")
@RequiredArgsConstructor
@Tag(name = "I18n")
public class I18nController {

    private final I18nFacade i18nFacade;

    @PatchMapping("/upsert")
    @Operation(operationId = "upsert-translations")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = SwaggerDescription.HTTP_200
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = SwaggerDescription.HTTP_400,
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = BlueDreamShishaErrorResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "401",
                    description = SwaggerDescription.HTTP_401,
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = BlueDreamShishaErrorResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "403",
                    description = SwaggerDescription.HTTP_403,
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = BlueDreamShishaErrorResponse.class)
                    )
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = SwaggerDescription.HTTP_500,
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = BlueDreamShishaErrorResponse.class)
                    )
            )
    })
    public ResponseEntity<String> upsertTranslations() throws BlueDreamShishaException {
        try {
            i18nFacade.upsertAllTranslations();
        } catch (IOException e) {
            throw new BlueDreamShishaException(
                    "Could not upsert translations",
                    ErrorCode.UNHANDLED,
                    HttpStatus.INTERNAL_SERVER_ERROR,
                    e
            );
        }
        return new ResponseEntity<>(
                "OK",
                HttpStatus.OK
        );
    }
}
