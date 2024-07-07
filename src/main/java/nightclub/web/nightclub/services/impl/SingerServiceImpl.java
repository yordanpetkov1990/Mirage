package nightclub.web.nightclub.services.impl;

import nightclub.web.nightclub.entities.Singer;
import nightclub.web.nightclub.entities.dtos.SingerDTO;
import nightclub.web.nightclub.repository.SingerRepository;
import nightclub.web.nightclub.services.SingerService;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class SingerServiceImpl implements SingerService {
    private final SingerRepository singerRepository;

    public SingerServiceImpl(SingerRepository singerRepository) {
        this.singerRepository = singerRepository;
    }

    @Override
    public Set<String> getAvailableSingers() {
        return singerRepository.findAll()
                .stream()
                .map(Singer::getName)
                .collect(Collectors.toSet());
    }

    @Override
    public Optional<Singer> getSingerByName(String name) {
        return this.singerRepository.findByName(name);
    }




}
