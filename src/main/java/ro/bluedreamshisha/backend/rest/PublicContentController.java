package ro.bluedreamshisha.backend.rest;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ro.bluedreamshisha.backend.exception.BlueDreamShishaException;
import ro.bluedreamshisha.backend.facade.PublicContentFacade;
import ro.bluedreamshisha.backend.model.swiper_image.SwiperImageDto;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("${api.url.base}/public")
@RequiredArgsConstructor
public class PublicContentController {

    private final PublicContentFacade publicContentFacade;

    @GetMapping("/swiper/images")
    @Operation(operationId = "find-swiper-images")
    public ResponseEntity<List<SwiperImageDto>> findSwiperImages() {
        return new ResponseEntity<>(
                publicContentFacade.findSwiperImages(),
                HttpStatus.OK
        );
    }

    @GetMapping(value = "/swiper/images/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
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
}
