package nightclub.web.nightclub.services;

import nightclub.web.nightclub.entities.Singer;
import nightclub.web.nightclub.entities.dtos.SingerDTO;

import java.util.Optional;
import java.util.Set;

public interface SingerService {

    Set<String> getAvailableSingers();

    Optional<Singer> getSingerByName(String name);
}
