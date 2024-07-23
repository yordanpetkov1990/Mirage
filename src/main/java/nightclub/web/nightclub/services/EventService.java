package nightclub.web.nightclub.services;

import nightclub.web.nightclub.entities.Event;
import nightclub.web.nightclub.entities.dtos.AddEventDTO;
import nightclub.web.nightclub.entities.dtos.EventDTO;
import nightclub.web.nightclub.entities.dtos.EventDetailsDTO;

import java.util.Optional;
import java.util.Set;

public interface EventService {


    void registerEvent(AddEventDTO addEventDTO);

    Set<EventDTO> getAllEvents();

    Set<EventDetailsDTO> getAllDetailedEvents();

    Optional<EventDetailsDTO> findEventById(Long id);

    Optional<Event> getEventById(Long eventId);

    void save(EventDetailsDTO eventDTO);

    void deleteById(Long id);
}
