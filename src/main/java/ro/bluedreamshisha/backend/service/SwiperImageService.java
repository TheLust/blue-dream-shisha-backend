package ro.bluedreamshisha.backend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.bluedreamshisha.backend.exception.NotFoundException;
import ro.bluedreamshisha.backend.model.swiper_image.SwiperImage;
import ro.bluedreamshisha.backend.repository.SwiperImageRepository;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SwiperImageService {

    private final SwiperImageRepository swiperImageRepository;

    public List<SwiperImage> findAll() {
        return swiperImageRepository.findAll();
    }

    public SwiperImage findById(UUID uuid) throws NotFoundException {
        return swiperImageRepository.findById(uuid)
                .orElseThrow(() -> new NotFoundException(List.of("uuid")));
    }

    public List<SwiperImage> findByEnabledOrderByIndex(Boolean enabled) {
        return swiperImageRepository.findByEnabledOrderByIndex(enabled);
    }

    public SwiperImage insert(SwiperImage swiperImage) {
        List<SwiperImage> recordsToUpdate = swiperImageRepository.findAllWithIndexGreaterThanOrEqualToOrderByIndexDesc(
                swiperImage.getIndex()
        );
        recordsToUpdate.forEach(recordToUpdate -> {
            recordToUpdate.setIndex(recordToUpdate.getIndex() + 1);
            swiperImageRepository.save(recordToUpdate);
        });
        return swiperImageRepository.save(swiperImage);
    }
}
