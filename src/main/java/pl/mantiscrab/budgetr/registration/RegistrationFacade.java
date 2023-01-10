package pl.mantiscrab.budgetr.registration;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@AllArgsConstructor
class RegistrationFacade {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    UserDto register(UserRegistrationDto dto) {
        UserRegistrationDto dtoWithEncodedPassword = encodePassword(dto);
        User user = UserMapper.userFromUserRegistrationDto(dtoWithEncodedPassword);
        User savedUser = userRepository.save(user);
        UserDto savedUserDto = UserMapper.userDtoFromUser(savedUser);
        return savedUserDto;
    }

    private UserRegistrationDto encodePassword(UserRegistrationDto registrationDto) {
        return new UserRegistrationDto(registrationDto.email(),
                registrationDto.username(),
                passwordEncoder.encode(registrationDto.password()));
    }
}
