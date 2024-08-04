package nightclub.web.nightclub.services.impl;

import nightclub.web.nightclub.entities.Image;
import nightclub.web.nightclub.repository.ImageRepository;
import nightclub.web.nightclub.services.ImageService;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;

    public ImageServiceImpl(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @Override
    public void saveImage(Image image) {
        this.imageRepository.save(image);
    }

    @Override
    @Transactional
    @Modifying
    public void deleteAllByEventId(Long eventId) {
        this.imageRepository.deleteAllByEventId(eventId);
    }
}
