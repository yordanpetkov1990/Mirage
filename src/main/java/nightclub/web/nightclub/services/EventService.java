package nightclub.web.nightclub.services;

import nightclub.web.nightclub.entities.Event;
import nightclub.web.nightclub.entities.dtos.AddEventDTO;
import nightclub.web.nightclub.entities.dtos.EventDTO;
import nightclub.web.nightclub.entities.dtos.EventDetailsDTO;

import java.util.Set;

public interface EventService {


    void registerEvent(AddEventDTO addEventDTO);

    Set<EventDTO> getAllEvents();

    EventDetailsDTO findEventById(Long id);

    Event getEventById(Long eventId);
}
