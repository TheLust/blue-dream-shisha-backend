package ro.bluedreamshisha.backend.repository;

import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ro.bluedreamshisha.backend.model.swiper_image.SwiperImage;

import java.util.List;
import java.util.UUID;

@Repository
public interface SwiperImageRepository extends JpaRepository<SwiperImage, UUID> {
    @Query("SELECT si FROM SwiperImage si WHERE si.index >= :index ORDER BY si.index DESC")
    List<SwiperImage> findAllWithIndexGreaterThanOrEqualToOrderByIndexDesc(@Param("index") @NotNull Integer index);

    List<SwiperImage> findByEnabledOrderByIndex(@NotNull Boolean enabled);
}
