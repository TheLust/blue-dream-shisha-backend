package ro.bluedreamshisha.backend.model;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ro.bluedreamshisha.backend.model.swiper_image.SwiperImage;
import ro.bluedreamshisha.backend.model.swiper_image.SwiperImageDto;

@Mapper(componentModel = "spring")
public interface ModelMapper {

    @Mapping(target = "fileId", source = "uuid")
    SwiperImageDto toDto(SwiperImage swiperImage);
}
