package nightclub.web.nightclub.services.impl;

import nightclub.web.nightclub.entities.Event;
import nightclub.web.nightclub.entities.Singer;
import nightclub.web.nightclub.entities.dtos.AddEventDTO;
import nightclub.web.nightclub.entities.dtos.EventDTO;
import nightclub.web.nightclub.entities.dtos.EventDetailsDTO;
import nightclub.web.nightclub.entities.dtos.SingerDTO;
import nightclub.web.nightclub.repository.EventRepository;
import nightclub.web.nightclub.services.EventService;
import nightclub.web.nightclub.services.SingerService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;

    private final SingerService singerService;
    private final ModelMapper modelMapper;

    public EventServiceImpl(EventRepository eventRepository, SingerService singerService, ModelMapper modelMapper) {
        this.eventRepository = eventRepository;
        this.singerService = singerService;
        this.modelMapper = modelMapper;
    }


    @Override
    public void registerEvent(AddEventDTO addEventDTO) {
        Event mappedEntity = this.modelMapper.map(addEventDTO, Event.class);
        Set<Singer> collect = addEventDTO.getSingersList().stream().map(s -> {
            Optional<Singer> singerByName = singerService.getSingerByName(s);

            return singerByName.orElseThrow();
        }).collect(Collectors.toSet());
        mappedEntity.setSingers(collect);
        this.eventRepository.save(mappedEntity);
    }

    @Override
    @Transactional
    public Set<EventDTO> getAllEvents() {
     return this.eventRepository.findAll()
             .stream().map(this::map).collect(Collectors.toSet());

    }

    @Override
    @Transactional
    public Set<EventDetailsDTO> getAllDetailedEvents() {
        return this.eventRepository.findAll().stream().map(this::mapToDetails).collect(Collectors.toSet());
    }

    @Override
    @Transactional
    public Optional<EventDetailsDTO> findEventById(Long id) {
     return this.eventRepository.findById(id).map(this::mapToDetails);
    }

    @Override
    public Optional<Event> getEventById(Long eventId) {
        return this.eventRepository.findById(eventId);
    }

    @Transactional
    protected EventDetailsDTO mapToDetails(Event event) {
        EventDetailsDTO map = modelMapper.map(event, EventDetailsDTO.class);
        map.setSingers(event.getSingers().stream().map(s -> modelMapper.map(s, SingerDTO.class)).collect(Collectors.toSet()));
        return map;
    }

    @Transactional
    protected EventDTO map(Event event) {
        EventDTO map = modelMapper.map(event, EventDTO.class);
        map.setSingers(event.getSingers().stream().map(Singer::getName).collect(Collectors.toList()));
        return map;
    }
}
