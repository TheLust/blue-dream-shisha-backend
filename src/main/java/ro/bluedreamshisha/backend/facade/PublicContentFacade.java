package ro.bluedreamshisha.backend.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import ro.bluedreamshisha.backend.client.FileManagementClient;
import ro.bluedreamshisha.backend.constant.ErrorCode;
import ro.bluedreamshisha.backend.exception.blue_dream_shisha_exception.BlueDreamShishaException;
import ro.bluedreamshisha.backend.exception.FileManagementException;
import ro.bluedreamshisha.backend.exception.NotFoundException;
import ro.bluedreamshisha.backend.model.ModelMapper;
import ro.bluedreamshisha.backend.model.swiper_image.SwiperImage;
import ro.bluedreamshisha.backend.model.swiper_image.SwiperImageDto;
import ro.bluedreamshisha.backend.service.SwiperImageService;

import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class PublicContentFacade {

    private final SwiperImageService swiperImageService;

    private final FileManagementClient fileManagementClient;

    private final ModelMapper mapper;

    public List<SwiperImageDto> findSwiperImages() {
        return swiperImageService.findByEnabledOrderByIndex(true)
                .stream()
                .map(mapper::toDto)
                .toList();
    }

    public byte[] findSwiperImage(UUID uuid) throws BlueDreamShishaException {
        try {
            SwiperImage swiperImage = swiperImageService.findById(uuid);
            return fileManagementClient.find(swiperImage.getFile());
        } catch (NotFoundException | FileManagementException e) {
            throw new BlueDreamShishaException(
                    "Could not find the swiper image of given uuid",
                    ErrorCode.NOT_FOUND,
                    HttpStatus.NOT_FOUND,
                    e
            );
        }
    }
}
