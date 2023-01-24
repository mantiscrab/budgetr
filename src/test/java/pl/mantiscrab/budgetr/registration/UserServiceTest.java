package pl.mantiscrab.budgetr.registration;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.mantiscrab.budgetr.registration.dto.UserDto;
import pl.mantiscrab.budgetr.registration.dto.UserRegisterDto;

import static org.junit.jupiter.api.Assertions.*;
import static pl.mantiscrab.budgetr.registration.TestUserData.sampleRegisterDto;

class UserServiceTest {
    private UserService userService;

    @BeforeEach
    void init() {
        userService = TestUserConfig.testUserService();
    }

    @Test
    @DisplayName("Should save employee")
    void shouldSaveEmployee() {
        //given
        UserRegisterDto registerDto = sampleRegisterDto().email("user@user").build();
        //when
        UserDto userDto = userService.register(registerDto);
        //then
        assertEquals(registerDto.email(), userDto.email());
        assertEquals(registerDto.username(), userDto.username());
    }

    @Test
    @DisplayName("Should throw exception when registering user and user with same email exist")
    void shouldThrowExceptionWhenRegisterAndUserWithSameEmailExists() {
        //given
        userService.register(sampleRegisterDto().email("user@user").build());
        //when-then
        assertThrows(UserAlreadyExistsException.class, () -> userService.register(sampleRegisterDto().email("user@user").build()));
        System.out.println();
    }
}
