package nightclub.web.nightclub.controller;

import nightclub.web.nightclub.entities.StatusEnum;
import nightclub.web.nightclub.entities.User;
import nightclub.web.nightclub.entities.UserDetails.UserDetailsEntity;
import nightclub.web.nightclub.entities.dtos.ShowReservationDTO;
import nightclub.web.nightclub.repository.UserRepository;
import nightclub.web.nightclub.services.ReservationService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDate;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @MockBean
    private ReservationService reservationService;


    @BeforeEach
    void setUp() {
        if (userRepository.findByUsername("monzre").isEmpty()) {
            User user = new User();
            user.setEmail("yordanpetkov@example.com");
            user.setFirstName("Yordan");
            user.setLastName("Petkov");
            user.setPassword(passwordEncoder.encode("123123"));
            user.setPhoneNumber("123123123");
            user.setUsername("monzre");
            userRepository.save(user);
        }
    }


    @Test
    void testRegistration() throws Exception {

        mockMvc.perform(post("/register")
                        .param("email", "yordanpetkov@123.com")
                        .param("firstName", "Yordan")
                        .param("lastName", "Petkov")
                        .param("password", "123123")
                        .param("phoneNumber", "1231231234")
                        .param("username", "monzre5")
                        .with(csrf())
                ).andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        Optional<User> userEntityOpt = userRepository.findByEmail("yordanpetkov@123.com");

        Assertions.assertTrue(userEntityOpt.isPresent());

        User userEntity = userEntityOpt.get();

        assertEquals("Yordan", userEntity.getFirstName());
        assertEquals("Petkov", userEntity.getLastName());
        assertEquals("1231231234", userEntity.getPhoneNumber());
        assertEquals("monzre5", userEntity.getUsername());

        Assertions.assertTrue(passwordEncoder.matches("123123", userEntity.getPassword()));
    }
}



