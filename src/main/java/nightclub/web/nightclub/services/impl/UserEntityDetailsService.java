package nightclub.web.nightclub.services.impl;


import nightclub.web.nightclub.entities.User;
import nightclub.web.nightclub.entities.UserDetails.UserDetailsEntity;
import nightclub.web.nightclub.entities.UserRoleEntity;
import nightclub.web.nightclub.entities.UserRoleEnum;
import nightclub.web.nightclub.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserEntityDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public UserEntityDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository
                .findByEmail(email)
                .map(this::map)
                .orElseThrow(
                        () -> new UsernameNotFoundException("User with email " + email + " not found!"));
    }

    private UserDetails map(User user) {
        return new UserDetailsEntity(
                user.getEmail(),
                user.getPassword(),
                user.getRoles().stream().map(UserRoleEntity::getRole).map(this::mapToRole).toList(),
                user.getFirstName(),
                user.getLastName(),
                user.getId()
        );
    }

    private GrantedAuthority mapToRole(UserRoleEnum role) {
        return new SimpleGrantedAuthority(
                "ROLE_" + role
        );
    }
}
