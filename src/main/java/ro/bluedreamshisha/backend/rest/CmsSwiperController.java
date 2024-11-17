package ro.bluedreamshisha.backend.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ro.bluedreamshisha.backend.constant.SwaggerDescription;
import ro.bluedreamshisha.backend.exception.blue_dream_shisha_exception.BlueDreamShishaException;
import ro.bluedreamshisha.backend.exception.blue_dream_shisha_exception.BlueDreamShishaErrorResponse;
import ro.bluedreamshisha.backend.facade.ContentManagementFacade;
import ro.bluedreamshisha.backend.model.auth.UserDetails;
import ro.bluedreamshisha.backend.model.swiper_image.SwiperImageDto;

@RestController
@RequestMapping("${api.url.base}/cms")
@RequiredArgsConstructor
@Tag(name = "Cms Swiper")
public class CmsSwiperController {

    private final ContentManagementFacade contentManagementFacade;

    @PostMapping(
            value = "/images",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    @Operation(operationId = "upload-swiper-image")
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
    public SwiperImageDto uploadSwiperImage(@RequestParam("order") Integer order,
                                            @RequestParam MultipartFile image,
                                            @AuthenticationPrincipal UserDetails requester) throws BlueDreamShishaException {
        return contentManagementFacade.uploadSwiperImage(image, order, requester);
    }
}
