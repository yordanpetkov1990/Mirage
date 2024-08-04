package nightclub.web.nightclub.services;

import nightclub.web.nightclub.entities.Image;

public interface ImageService {

    void saveImage(Image image);

    void deleteAllByEventId(Long eventId);
}
