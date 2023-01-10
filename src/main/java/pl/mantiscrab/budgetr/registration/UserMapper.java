package pl.mantiscrab.budgetr.registration;

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
