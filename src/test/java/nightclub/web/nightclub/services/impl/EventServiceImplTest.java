package nightclub.web.nightclub.services.impl;
import nightclub.web.nightclub.entities.Event;
import nightclub.web.nightclub.entities.Singer;
import nightclub.web.nightclub.entities.dtos.AddEventDTO;
import nightclub.web.nightclub.entities.dtos.EventDTO;
import nightclub.web.nightclub.entities.dtos.EventDetailsDTO;
import nightclub.web.nightclub.entities.dtos.SingerDTO;
import nightclub.web.nightclub.repository.EventRepository;
import nightclub.web.nightclub.services.EventService;
import nightclub.web.nightclub.services.ImageService;
import nightclub.web.nightclub.services.SingerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EventServiceImplTest {

    @InjectMocks
    private EventServiceImpl eventService;

    @Mock
    private EventRepository eventRepository;

    @Mock
    private SingerService singerService;

    @Mock
    private ImageService imageService;


    @Mock
    private ModelMapper modelMapper;
    @Captor
    private ArgumentCaptor<Event> eventCaptor;

    @BeforeEach
    void setUp() {
        eventService = new EventServiceImpl(eventRepository, singerService,modelMapper,imageService);
    }

    @Test
    void testRegisterEvent() {
        // Arrange
        AddEventDTO addEventDTO = new AddEventDTO();
        addEventDTO.setName("Event Name");
        addEventDTO.setDate(LocalDate.now());
        addEventDTO.setSingersList(List.of("Singer1", "Singer2"));

        Singer singer1 = new Singer();
        singer1.setName("Singer1");

        Singer singer2 = new Singer();
        singer2.setName("Singer2");

        when(singerService.getSingerByName("Singer1")).thenReturn(Optional.of(singer1));
        when(singerService.getSingerByName("Singer2")).thenReturn(Optional.of(singer2));
        when(modelMapper.map(addEventDTO, Event.class)).thenReturn(new Event().setName(addEventDTO.getName()).setDate(LocalDate.now()).setSingers(Set.of(singer1,singer2)));

        // Act
        eventService.registerEvent(addEventDTO);

        // Assert
        verify(eventRepository).save(eventCaptor.capture());
        Event event = eventCaptor.getValue();
        assertEquals("Event Name", event.getName());
        assertEquals(LocalDate.now(), event.getDate());
        assertTrue(event.getSingers().containsAll(Set.of(singer1, singer2)));
    }

    @Test
    void testGetAllEvents() {
        // Arrange
        Event event = new Event();
        EventDTO eventDTO = new EventDTO();
        when(eventRepository.findAll()).thenReturn(Collections.singletonList(event));
        when(modelMapper.map(any(Event.class), eq(EventDTO.class))).thenReturn(eventDTO);

        // Act
        Set<EventDTO> result = eventService.getAllEvents();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertTrue(result.contains(eventDTO));
    }

    @Test
    void testFindEventById() {
        // Arrange
        Long eventId = 1L;
        Event event = new Event();
        EventDetailsDTO eventDetailsDTO = new EventDetailsDTO();
        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));
        when(modelMapper.map(any(Event.class), eq(EventDetailsDTO.class))).thenReturn(eventDetailsDTO);

        // Act
        Optional<EventDetailsDTO> result = eventService.findEventById(eventId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(eventDetailsDTO, result.get());
    }

    @Test
    void testGetEventById() {
        // Arrange
        Long eventId = 1L;
        Event event = new Event();
        when(eventRepository.findById(eventId)).thenReturn(Optional.of(event));

        // Act
        Optional<Event> result = eventService.getEventById(eventId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(event, result.get());
    }
}