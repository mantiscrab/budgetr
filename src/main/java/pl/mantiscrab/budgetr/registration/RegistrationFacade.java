package pl.mantiscrab.budgetr.registration;

import lombok.AllArgsConstructor;

@AllArgsConstructor
class RegistrationFacade {
    private final UserRepository userRepository;

    UserDto register(UserRegistrationDto registrationDto) {
        User user = UserMapper.userFromUserRegistrationDto(registrationDto);
        User savedUser = userRepository.save(user);
        UserDto savedUserDto = UserMapper.userDtoFromUser(savedUser);
        return savedUserDto;
    }
}
