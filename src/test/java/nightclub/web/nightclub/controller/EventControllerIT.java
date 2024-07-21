package nightclub.web.nightclub.controller;

import nightclub.web.nightclub.entities.Event;
import nightclub.web.nightclub.entities.dtos.AddEventDTO;
import nightclub.web.nightclub.entities.dtos.EventDTO;
import nightclub.web.nightclub.entities.dtos.EventDetailsDTO;
import nightclub.web.nightclub.services.EventService;
import nightclub.web.nightclub.services.SingerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class EventControllerIT {

    private MockMvc mockMvc;

    @Mock
    private SingerService singerService;

    @Mock
    private EventService eventService;

    @InjectMocks
    private EventController eventController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(eventController).build();
    }





    @Test
    void testDoAddEventSuccess() throws Exception {
        AddEventDTO addEventDTO = new AddEventDTO(); // Populate with valid data
        mockMvc.perform(post("/admin/add-event")
                        .flashAttr("eventDTO", addEventDTO))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }



    @Test
    void testGetEventDetails() throws Exception {
        EventDetailsDTO eventDetails = new EventDetailsDTO();
        when(eventService.findEventById(anyLong())).thenReturn(Optional.of(eventDetails));

        mockMvc.perform(get("/event/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("event-details"))
                .andExpect(model().attributeExists("event"))
                .andExpect(model().attribute("event", eventDetails));
    }
}