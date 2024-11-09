package ro.bluedreamshisha.backend.rest;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ro.bluedreamshisha.backend.exception.BlueDreamShishaException;
import ro.bluedreamshisha.backend.facade.ContentManagementFacade;
import ro.bluedreamshisha.backend.model.swiper_image.SwiperImageDto;

@RestController
@RequestMapping("${api.url.base}/cms")
@RequiredArgsConstructor
public class ContentManagementController {

    private final ContentManagementFacade contentManagementFacade;

    @PostMapping(value = "/swiper/images", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(operationId = "upload-swiper-image")
    public SwiperImageDto uploadSwiperImage(@RequestParam("order") Integer order,
                                            @RequestParam MultipartFile image) throws BlueDreamShishaException {
        return contentManagementFacade.uploadSwiperImage(image, order);
    }
}
