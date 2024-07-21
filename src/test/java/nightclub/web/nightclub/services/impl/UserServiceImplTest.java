package nightclub.web.nightclub.services.impl;

import nightclub.web.nightclub.entities.User;
import nightclub.web.nightclub.entities.dtos.RegistrationDTO;
import nightclub.web.nightclub.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    private UserServiceImpl toTest;

    @Captor
    private ArgumentCaptor<User> userEntityCaptor;

    @Mock
    private UserRepository mockUserRepository;

    @Mock
    private PasswordEncoder mockPasswordEncoder;

    @BeforeEach
    void setUp() {

        toTest = new UserServiceImpl(
                mockUserRepository,
                new ModelMapper(),
                mockPasswordEncoder
        );

    }

    @Test
    void testUserRegistration() {


        RegistrationDTO registrationDTO =
                new RegistrationDTO()
                        .setFirstName("Yordan")
                        .setLastName("Petkov")
                        .setPassword("123123")
                        .setEmail("yordanpetkov@example.com")
                        .setUsername("yordanpetkov")
                        .setPhoneNumber("1012321312123");

        when(mockPasswordEncoder.encode(registrationDTO.getPassword()))
                .thenReturn(registrationDTO.getPassword() + registrationDTO.getPassword());



        toTest.registerUser(registrationDTO);


        verify(mockUserRepository).save(userEntityCaptor.capture());

        User actualSavedEntity = userEntityCaptor.getValue();

        Assertions.assertNotNull(actualSavedEntity);
        Assertions.assertEquals(registrationDTO.getFirstName(), actualSavedEntity.getFirstName());
        Assertions.assertEquals(registrationDTO.getLastName(), actualSavedEntity.getLastName());
        Assertions.assertEquals(registrationDTO.getPassword() + registrationDTO.getPassword(),
                actualSavedEntity.getPassword());
        Assertions.assertEquals(registrationDTO.getEmail(), actualSavedEntity.getEmail());
        Assertions.assertEquals(registrationDTO.getPhoneNumber(), actualSavedEntity.getPhoneNumber());
        Assertions.assertEquals(registrationDTO.getUsername(), actualSavedEntity.getUsername());

    }
}