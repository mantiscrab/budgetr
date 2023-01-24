package pl.mantiscrab.budgetr.registration;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.mantiscrab.budgetr.registration.dto.UserDto;
import pl.mantiscrab.budgetr.registration.dto.UserRegisterDto;

@AllArgsConstructor
class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    UserDto register(UserRegisterDto dto) {
        throwExceptionIfUserAlreadyExists(dto);
        User user = mapUserDtoToUser(dto);
        User savedUser = userRepository.save(user);
        UserDto savedUserDto = UserMapper.userDtoFromUser(savedUser);
        return savedUserDto;
    }

    private void throwExceptionIfUserAlreadyExists(UserRegisterDto dto) {
        if (userRepository.findByEmail(dto.email()).isPresent())
            throw new UserAlreadyExistsException("User with email \"" + dto.email() + "\" already exist");
        if (userRepository.findByUsername(dto.username()).isPresent())
            throw new UserAlreadyExistsException("User with username \"" + dto.username() + "\" already exist");
    }

    private User mapUserDtoToUser(UserRegisterDto dto) {
        UserRegisterDto dtoWithEncodedPassword = encodePassword(dto);
        User user = UserMapper.userFromUserRegisterDto(dtoWithEncodedPassword);
        return user;
    }

    private UserRegisterDto encodePassword(UserRegisterDto registrationDto) {
        return new UserRegisterDto(registrationDto.email(),
                registrationDto.username(),
                passwordEncoder.encode(registrationDto.password()));
    }
}
