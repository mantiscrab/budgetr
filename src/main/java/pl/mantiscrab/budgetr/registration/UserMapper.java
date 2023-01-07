package pl.mantiscrab.budgetr.registration;

class UserMapper {
    static User userFromUserRegistrationDto(UserRegistrationDto registrationDto) {
        return User.builder()
                .email(registrationDto.getEmail())
                .username(registrationDto.getUsername())
                .password(registrationDto.getPassword())
                .build();
    }

    static UserDto userDtoFromUser(User user) {
        return new UserDto(user.getEmail(), user.getUsername());
    }
}
