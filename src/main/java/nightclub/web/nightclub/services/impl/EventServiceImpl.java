package nightclub.web.nightclub.services.impl;

import nightclub.web.nightclub.entities.Event;
import nightclub.web.nightclub.entities.Image;
import nightclub.web.nightclub.entities.Singer;
import nightclub.web.nightclub.entities.dtos.*;
import nightclub.web.nightclub.repository.EventRepository;
import nightclub.web.nightclub.services.EventService;
import nightclub.web.nightclub.services.ImageService;
import nightclub.web.nightclub.services.SingerService;
import org.modelmapper.ModelMapper;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;

    private final SingerService singerService;
    private final ModelMapper modelMapper;

    private final ImageService imageService;

    public EventServiceImpl(EventRepository eventRepository, SingerService singerService, ModelMapper modelMapper, ImageService imageService) {
        this.eventRepository = eventRepository;
        this.singerService = singerService;
        this.modelMapper = modelMapper;
        this.imageService = imageService;
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

    @Override
    @Transactional
    public void save(EditEventDTO eventDTO) {
        this.eventRepository.saveAndFlush(mapToEvent(eventDTO));
    }

    @Override
    @Transactional
    @Modifying
    public void deleteById(Long id) {
        this.imageService.deleteAllByEventId(id);
        this.eventRepository.deleteById(id);
    }

    @Override
    @Transactional
    @Modifying
    public void removeAllOlderThan2Months() {
        this.eventRepository.deleteAllByDateLessThan(LocalDate.now().minusMonths(2));
    }

    @Override
    @Transactional
    public List<EventDTO> getAllFutureEvents() {
        return this.eventRepository.findAllByDateGreaterThanEqualOrderByDate(LocalDate.now()).stream().map(this::map).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public EditEventDTO findEventByIdToEdit(Long id) {
        Optional<Event> byId = this.eventRepository.findById(id);
        EditEventDTO map = this.modelMapper.map(byId, EditEventDTO.class);
        map.setSingersName(byId.get().getSingers().stream().map(Singer::getName).collect(Collectors.toList()));
        return map;
    }

    @Transactional
    protected EventDetailsDTO mapToDetails(Event event) {
        EventDetailsDTO map = modelMapper.map(event, EventDetailsDTO.class);
        map.setSingers(event.getSingers().stream().map(s -> modelMapper.map(s, SingerDTO.class)).collect(Collectors.toSet()));
        map.setImageUrls(event.getImages().stream().map(Image::getPathUrl).collect(Collectors.toList()));
        return map;
    }

    @Transactional
    protected EventDTO map(Event event) {
        EventDTO map = modelMapper.map(event, EventDTO.class);
        map.setSingers(event.getSingers().stream().map(Singer::getName).collect(Collectors.toList()));
        return map;
    }

    @Transactional
    protected Event mapToEvent(EditEventDTO eventDTO) {
        Event map = modelMapper.map(eventDTO, Event.class);
        map.setSingers(eventDTO.getSingersName().stream().map(s -> this.singerService.getSingerByName(s).get()).collect(Collectors.toSet()));
        return map;
    }
}
