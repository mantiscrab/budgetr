package pl.mantiscrab.budgetr.auth;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.mantiscrab.budgetr.auth.dto.UserDto;
import pl.mantiscrab.budgetr.auth.dto.UserRegisterDto;

@AllArgsConstructor
class UserAuthService {
    private final PasswordEncoder passwordEncoder;
    private final UserAuthRepository userAuthRepository;

    UserDto register(UserRegisterDto dto) {
        throwExceptionIfUserAlreadyExists(dto);
        UserAuth user = createUser(dto);
        UserAuth savedUser = userAuthRepository.save(user);
        return userDtoFromUser(savedUser);
    }

    private void throwExceptionIfUserAlreadyExists(UserRegisterDto dto) {
        if (userAuthRepository.findByEmail(dto.email()).isPresent())
            throw UserAlreadyExistsException.withEmail(dto.email());
        if (userAuthRepository.findByUsername(dto.username()).isPresent())
            throw UserAlreadyExistsException.withUsername(dto.username());
    }

    private UserAuth createUser(UserRegisterDto dto) {
        String encodedPassword = passwordEncoder.encode(dto.password());
        UserAuth user = UserAuth.builder()
                .email(dto.email())
                .username(dto.username())
                .password(encodedPassword)
                .enabled(true)
                .build();
        Authority userAuthority = new Authority(user, "ROLE_USER");
        user.addAuthority(userAuthority);
        return user;
    }

    static UserDto userDtoFromUser(UserAuth user) {
        return new UserDto(user.getEmail(), user.getUsername());
    }
}
