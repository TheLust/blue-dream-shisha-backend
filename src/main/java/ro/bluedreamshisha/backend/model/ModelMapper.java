package ro.bluedreamshisha.backend.model;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ro.bluedreamshisha.backend.model.auth.Account;
import ro.bluedreamshisha.backend.model.auth.User;
import ro.bluedreamshisha.backend.model.request.LoginRequest;
import ro.bluedreamshisha.backend.model.request.RegisterRequest;
import ro.bluedreamshisha.backend.model.swiper_image.SwiperImage;
import ro.bluedreamshisha.backend.model.swiper_image.SwiperImageDto;

@Mapper(componentModel = "spring")
public interface ModelMapper {

    @Mapping(target = "fileId", source = "uuid")
    SwiperImageDto toDto(SwiperImage swiperImage);

    @Mapping(target = "user.username", source = "username")
    @Mapping(target = "user.password", source = "password")
    Account toEntity(RegisterRequest registerRequest);
}
