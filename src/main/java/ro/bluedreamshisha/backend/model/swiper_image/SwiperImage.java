package ro.bluedreamshisha.backend.model.swiper_image;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ro.bluedreamshisha.backend.model.file_management.File;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class SwiperImage {

    @Id
    private UUID uuid;

    @MapsId
    @OneToOne
    private File file;

    @Column(nullable = false, unique = true)
    private Integer index;

    @Column(nullable = false)
    private Boolean enabled;
}
