package ro.bluedreamshisha.backend.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.bluedreamshisha.backend.constant.SwaggerDescription;
import ro.bluedreamshisha.backend.exception.blue_dream_shisha_exception.BlueDreamShishaException;
import ro.bluedreamshisha.backend.exception.blue_dream_shisha_exception.ServiceError;
import ro.bluedreamshisha.backend.facade.I18nFacade;
import ro.bluedreamshisha.backend.facade.PublicContentFacade;
import ro.bluedreamshisha.backend.model.swiper_image.SwiperImageDto;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("${api.url.base}/public")
@RequiredArgsConstructor
@Tag(name = "Public Content")
public class PublicContentController {

    private final PublicContentFacade publicContentFacade;
    private final I18nFacade i18nFacade;

    @GetMapping("/swiper/images")
    @Operation(operationId = "find-swiper-images")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = SwaggerDescription.HTTP_200
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = SwaggerDescription.HTTP_400,
                    content = @Content(schema = @Schema(implementation = ServiceError.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = SwaggerDescription.HTTP_500,
                    content = @Content(schema = @Schema(implementation = ServiceError.class))
            )
    })
    public ResponseEntity<List<SwiperImageDto>> findSwiperImages() {
        return new ResponseEntity<>(
                publicContentFacade.findSwiperImages(),
                HttpStatus.OK
        );
    }

    @GetMapping(value = "/swiper/images/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    @Operation(operationId = "find-swiper-image")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = SwaggerDescription.HTTP_200
            ),
            @ApiResponse(
                    responseCode = "400",
                    description = SwaggerDescription.HTTP_400,
                    content = @Content(schema = @Schema(implementation = ServiceError.class))
            ),
            @ApiResponse(
                    responseCode = "404",
                    description = SwaggerDescription.HTTP_404,
                    content = @Content(schema = @Schema(implementation = ServiceError.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = SwaggerDescription.HTTP_500,
                    content = @Content(schema = @Schema(implementation = ServiceError.class))
            )
    })
    public ResponseEntity<byte[]> findSwiperImage(@PathVariable("id") UUID uuid) throws BlueDreamShishaException {

        byte[] imageBytes = publicContentFacade.findSwiperImage(uuid);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);

        return new ResponseEntity<>(
                imageBytes,
                headers,
                HttpStatus.OK
        );
    }

    @GetMapping(
            value = "/i18n/languages/{language}",
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
                    content = @Content(schema = @Schema(implementation = ServiceError.class))
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = SwaggerDescription.HTTP_500,
                    content = @Content(schema = @Schema(implementation = ServiceError.class))
            )
    })
    public Map<String, String> findTranslations(@PathVariable("language") String language) {
        return i18nFacade.findTranslations(language);
    }
}
