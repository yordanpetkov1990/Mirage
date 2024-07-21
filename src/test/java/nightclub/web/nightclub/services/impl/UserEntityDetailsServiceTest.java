package nightclub.web.nightclub.services.impl;

import nightclub.web.nightclub.entities.User;
import nightclub.web.nightclub.entities.UserDetails.UserDetailsEntity;
import nightclub.web.nightclub.entities.UserRoleEntity;
import nightclub.web.nightclub.entities.UserRoleEnum;
import nightclub.web.nightclub.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserEntityDetailsServiceTest {
    private static final String TEST_EMAIL = "user@example.com";
    private static final String NOT_EXISTENT_EMAIL = "noone@example.com";

    private UserEntityDetailsService toTest;
    @Mock
    private UserRepository mockUserRepository;

    @BeforeEach
    void setUp() {
        toTest = new UserEntityDetailsService(mockUserRepository);
    }

    @Test
    void testLoadUserByUsername_UserFound() {


        User testUser = new User()
                .setEmail(TEST_EMAIL)
                .setPassword("123123")
                .setFirstName("Pesho")
                .setLastName("Petrov")
                .setPhoneNumber("123123123")
                .setRoles(List.of(
                        new UserRoleEntity().setRole(UserRoleEnum.ADMIN),
                        new UserRoleEntity().setRole(UserRoleEnum.USER)
                ));

        when(mockUserRepository.findByEmail(TEST_EMAIL))
                .thenReturn(Optional.of(testUser));


        UserDetails userDetails = toTest.loadUserByUsername(TEST_EMAIL);


        Assertions.assertInstanceOf(UserDetailsEntity.class, userDetails);

        UserDetailsEntity userDetailsEntity = (UserDetailsEntity) userDetails;

        Assertions.assertEquals(TEST_EMAIL, userDetails.getUsername());
        Assertions.assertEquals(testUser.getPassword(), userDetails.getPassword());
        Assertions.assertEquals(testUser.getFirstName(), userDetailsEntity.getFirstName());
        Assertions.assertEquals(testUser.getLastName(), userDetailsEntity.getLastName());
        Assertions.assertEquals(testUser.getPhoneNumber(), userDetailsEntity.getPhoneNumber());
        Assertions.assertEquals(testUser.getFirstName() + " " + testUser.getLastName(),
                userDetailsEntity.getFullName());

        var expectedRoles = testUser.getRoles().stream().map(UserRoleEntity::getRole).map(r -> "ROLE_" + r).toList();
        var actualRoles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();

        Assertions.assertEquals(expectedRoles, actualRoles);
    }

    @Test
    void testLoadUserByUsername_UserNotFound() {
        Assertions.assertThrows(
                UsernameNotFoundException.class,
                () -> toTest.loadUserByUsername(NOT_EXISTENT_EMAIL)
        );
    }
}
