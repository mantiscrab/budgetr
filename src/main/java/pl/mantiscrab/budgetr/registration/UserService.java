package pl.mantiscrab.budgetr.registration;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import pl.mantiscrab.budgetr.registration.dto.UserDto;
import pl.mantiscrab.budgetr.registration.dto.UserRegisterDto;

@AllArgsConstructor
class UserService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Transactional
    UserDto register(UserRegisterDto dto) {
        UserRegisterDto dtoWithEncodedPassword = encodePassword(dto);
        User user = UserMapper.userFromUserRegisterDto(dtoWithEncodedPassword);
        User savedUser = userRepository.save(user);
        UserDto savedUserDto = UserMapper.userDtoFromUser(savedUser);
        return savedUserDto;
    }

    private UserRegisterDto encodePassword(UserRegisterDto registrationDto) {
        return new UserRegisterDto(registrationDto.email(),
                registrationDto.username(),
                passwordEncoder.encode(registrationDto.password()));
    }
}
