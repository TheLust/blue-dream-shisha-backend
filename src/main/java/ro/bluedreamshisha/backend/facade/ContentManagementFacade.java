package ro.bluedreamshisha.backend.facade;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import ro.bluedreamshisha.backend.exception.BlueDreamShishaException;
import ro.bluedreamshisha.backend.model.ModelMapper;
import ro.bluedreamshisha.backend.model.file_management.File;
import ro.bluedreamshisha.backend.model.file_management.FileCategory;
import ro.bluedreamshisha.backend.model.swiper_image.SwiperImage;
import ro.bluedreamshisha.backend.model.swiper_image.SwiperImageDto;
import ro.bluedreamshisha.backend.service.FileManagementService;
import ro.bluedreamshisha.backend.service.SwiperImageService;

@Component
@RequiredArgsConstructor
public class ContentManagementFacade {

    private final FileManagementService fileManagementService;

    private final SwiperImageService swiperImageService;

    private final ModelMapper mapper;



    public SwiperImageDto uploadSwiperImage(MultipartFile image, Integer order) throws BlueDreamShishaException {
        //TODO: validation
        File file = fileManagementService.insert(image, FileCategory.PUBLIC);
        return mapper.toDto(
                swiperImageService.insert(new SwiperImage(file.getUuid(), file, order, true))
        );
    }
}
