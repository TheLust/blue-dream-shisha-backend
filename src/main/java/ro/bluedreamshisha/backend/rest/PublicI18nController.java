package ro.bluedreamshisha.backend.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.bluedreamshisha.backend.constant.SwaggerDescription;
import ro.bluedreamshisha.backend.exception.blue_dream_shisha_exception.BlueDreamShishaErrorResponse;
import ro.bluedreamshisha.backend.facade.I18nFacade;

import java.util.Map;

@RestController
@RequestMapping("${api.url.base}/public/i18n")
@RequiredArgsConstructor
@Tag(name = "Public I18n")
public class PublicI18nController {

    private final I18nFacade i18nFacade;

    @GetMapping(
            value = "/languages/{language}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(operationId = "get-translations")
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
                    responseCode = "500",
                    description = SwaggerDescription.HTTP_500,
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = BlueDreamShishaErrorResponse.class)
                    )
            )
    })
    public Map<String, String> findTranslations(@PathVariable("language") String language) {
        return i18nFacade.findTranslations(language);
    }
}
