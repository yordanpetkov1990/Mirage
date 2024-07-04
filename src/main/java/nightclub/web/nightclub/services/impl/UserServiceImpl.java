package nightclub.web.nightclub.services.impl;

import nightclub.web.nightclub.entities.User;
import nightclub.web.nightclub.entities.dtos.RegistrationDTO;
import nightclub.web.nightclub.repository.UserRepository;
import nightclub.web.nightclub.services.userService;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements userService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void registerUser(RegistrationDTO registrationDTO) {
        userRepository.save(map(registrationDTO));
    }

    private User map(RegistrationDTO registrationDTO) {
        User mappedEntity = modelMapper.map(registrationDTO, User.class);

        mappedEntity.setPassword(passwordEncoder.encode(registrationDTO.getPassword()));

        return mappedEntity;
    }
}
