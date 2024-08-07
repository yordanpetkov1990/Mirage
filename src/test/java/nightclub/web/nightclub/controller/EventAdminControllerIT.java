package nightclub.web.nightclub.controller;

import nightclub.web.nightclub.entities.Event;
import nightclub.web.nightclub.entities.Image;
import nightclub.web.nightclub.entities.dtos.EditEventDTO;
import nightclub.web.nightclub.entities.dtos.EventDetailsDTO;
import nightclub.web.nightclub.services.EventService;
import nightclub.web.nightclub.services.ImageService;
import nightclub.web.nightclub.services.SingerService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;

@SpringBootTest
@AutoConfigureMockMvc
public class EventAdminControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EventService eventService;

    @MockBean
    private SingerService singerService;

    @MockBean
    private ImageService imageService;

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testListEvents() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/events"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("list-events"));
    }
    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testEditEventGet() throws Exception {
        EditEventDTO editEventDTO = new EditEventDTO();
        editEventDTO.setId(1L);
        editEventDTO.setName("Test Event");

        Mockito.when(eventService.findEventByIdToEdit(1L)).thenReturn(editEventDTO);

        mockMvc.perform(MockMvcRequestBuilders.get("/admin/events/edit/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("events-edit"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("EditEventDTO"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testEditEventPost() throws Exception {
        EditEventDTO editEventDTO = new EditEventDTO();
        editEventDTO.setId(1L);
        editEventDTO.setName("Updated Event");

        mockMvc.perform(MockMvcRequestBuilders.post("/admin/events/edit")
                        .param("id", "1")
                        .param("name", "Updated Event")
                        .param("description", "Updated Descriptionnnnnnnnnnnnnnnnnnnnnn")
                        .param("date", "2026-01-01")
                        .param("startTime", "12:00")
                        .param("endTime", "15:00")
                        .param("entryFee", "20.00")
                        .param("capacity", "100")
                        .with(csrf()))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/admin/events"));

        Mockito.verify(eventService, Mockito.times(1)).save(Mockito.any(EditEventDTO.class));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testDeleteEvent() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/admin/events/delete/1")
                        .with(csrf()))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/admin/events"));

        Mockito.verify(eventService, Mockito.times(1)).deleteById(1L);
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testShowAddImageForm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/events/add-image"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("add-image-to-event"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("events"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testAddImageToEvent() throws Exception {
        MockMultipartFile file = new MockMultipartFile("image", "test.jpg", "image/jpeg", "test image content".getBytes());

        Mockito.when(eventService.getEventById(1L)).thenReturn(Optional.of(new Event()));

        mockMvc.perform(MockMvcRequestBuilders.multipart("/admin/events/add-image")
                        .file(file)
                        .param("eventId", "1")
                        .with(csrf())
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(MockMvcResultMatchers.status().is3xxRedirection())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/admin/events/add-image?success"));

        Mockito.verify(imageService, Mockito.times(1)).saveImage(Mockito.any(Image.class));
    }
}
