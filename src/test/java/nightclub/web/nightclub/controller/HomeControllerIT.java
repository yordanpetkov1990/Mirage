package nightclub.web.nightclub.controller;

import nightclub.web.nightclub.entities.FAQ;
import nightclub.web.nightclub.services.FAQService;
import nightclub.web.nightclub.services.JobService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;
import java.util.Set;

import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class HomeControllerIT {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FAQService faqService;

    @MockBean
    private JobService jobService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void testIndex() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("index"));
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void testHome() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/home"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("home"));
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void testContact() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/contact"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("contact"));
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void testAbout() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/about"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("about"));
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void testFaq() throws Exception {
        // Prepare mock data
        when(faqService.getAllFAQs()).thenReturn(Set.of(new FAQ(),new FAQ()));

        mockMvc.perform(MockMvcRequestBuilders.get("/faq"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("faq"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("faqs"));
    }
}
