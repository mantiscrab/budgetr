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
        User user = createUser(dto);
        User savedUser = userRepository.save(user);
        return userDtoFromUser(savedUser);
    }

    private void throwExceptionIfUserAlreadyExists(UserRegisterDto dto) {
        if (userRepository.findByEmail(dto.email()).isPresent())
            throw UserAlreadyExistsException.withEmail(dto.email());
        if (userRepository.findByUsername(dto.username()).isPresent())
            throw UserAlreadyExistsException.withUsername(dto.username());
    }

    private User createUser(UserRegisterDto dto) {
        String encodedPassword = passwordEncoder.encode(dto.password());
        User user = User.builder()
                .email(dto.email())
                .username(dto.username())
                .password(encodedPassword)
                .enabled(true)
                .build();
        Authority userAuthority = new Authority(user, "ROLE_USER");
        user.addAuthority(userAuthority);
        return user;
    }

    static UserDto userDtoFromUser(User user) {
        return new UserDto(user.getEmail(), user.getUsername());
    }
}
