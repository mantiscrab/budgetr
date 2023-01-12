package pl.mantiscrab.budgetr.registration;

import pl.mantiscrab.budgetr.registration.dto.UserDto;
import pl.mantiscrab.budgetr.registration.dto.UserRegisterDto;

class UserMapper {
    static User userFromUserRegisterDto(UserRegisterDto registrationDto) {
        return User.builder()
                .email(registrationDto.email())
                .username(registrationDto.username())
                .password(registrationDto.password())
                .build();
    }

    static UserDto userDtoFromUser(User user) {
        return new UserDto(user.getEmail(), user.getUsername());
    }
}
