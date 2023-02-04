package pl.mantiscrab.budgetr.registration;

import pl.mantiscrab.budgetr.registration.dto.UserDto;

class UserMapper {
    static UserDto userDtoFromUser(User user) {
        return new UserDto(user.getEmail(), user.getUsername());
    }
}
