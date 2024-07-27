package nightclub.web.nightclub.controller;

import jakarta.transaction.Transactional;
import nightclub.web.nightclub.entities.Job;
import nightclub.web.nightclub.services.JobService;
import nightclub.web.nightclub.services.impl.EmailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Optional;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class JobControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JobService jobService;

    @MockBean
    private EmailService emailService;

    private Job testJob;

    @BeforeEach
    public void setUp() {

        testJob = new Job();
        testJob.setId(1L);
        testJob.setTitle("Sample Job Title");
        testJob.setDescription("Sample Job Description");
        testJob.setRequirements("Sample Job Requirements");

        Mockito.when(jobService.findById(1L)).thenReturn(Optional.of(testJob));
    }

    @Test
    @WithMockUser(username = "user", roles = {"ADMIN","USER"})
    public void testShowAddJobForm() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/admin/add-job"))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.view().name("add-job"));
    }

    @Test
    @WithMockUser(username = "user", roles = {"ADMIN","USER"})
    public void testAddJob() throws Exception {
        mockMvc.perform(post("/admin/add-job")
                        .param("title", "test")
                        .param("description", "test test")
                        .param("requirements", "test test test")
                        .with(csrf())
                ).andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/careers"));
    }



}
