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

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
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
        AddEventDTO addEventDTO = new AddEventDTO();
        addEventDTO.setName("Awesome Event");
        addEventDTO.setDescription("This is an awesome event you don't want to miss.");
        addEventDTO.setDate(LocalDate.now().plusDays(1));
        addEventDTO.setStartTime(LocalTime.of(20, 0));
        addEventDTO.setEndTime(LocalTime.of(23, 0));
        addEventDTO.setEntryFee(BigDecimal.valueOf(20.00));
        addEventDTO.setCapacity(100);
        addEventDTO.setSingersList(List.of("Singer1", "Singer2"));

        mockMvc.perform(post("/admin/add-event")
                        .flashAttr("addEventDTO", addEventDTO))
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