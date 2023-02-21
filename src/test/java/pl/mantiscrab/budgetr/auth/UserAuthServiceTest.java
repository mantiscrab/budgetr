package pl.mantiscrab.budgetr.auth;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import pl.mantiscrab.budgetr.auth.dto.UserDto;
import pl.mantiscrab.budgetr.auth.dto.UserRegisterDto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static pl.mantiscrab.budgetr.auth.UserTestDataProvider.sampleRegisterDto;

class UserAuthServiceTest {
    private UserAuthService userAuthService;

    @BeforeEach
    void init() {
        userAuthService = UserTestConfig.userService();
    }

    @Test
    @DisplayName("Should save employee")
    void shouldSaveEmployee() {
        //given
        UserRegisterDto registerDto = sampleRegisterDto().email("user@user").build();
        //when
        UserDto userDto = userAuthService.register(registerDto);
        //then
        assertEquals(registerDto.email(), userDto.email());
        assertEquals(registerDto.username(), userDto.username());
    }

    @Test
    @DisplayName("Should throw exception when registering user and user with same email exist")
    void shouldThrowExceptionWhenRegisterAndUserWithSameEmailExists() {
        //given
        userAuthService.register(sampleRegisterDto().email("user@user").build());
        //when-then
        assertThrows(UserAlreadyExistsException.class, () -> userAuthService.register(sampleRegisterDto().email("user@user").build()));
        System.out.println();
    }

    @Test
    @DisplayName("Should throw exception when registering user and user with same email exist")
    void shouldThrowExceptionWhenRegisterAndUserWithSameUsernameExists() {
        //given
        userAuthService.register(sampleRegisterDto().email("firstUser@user").username("username").build());
        //when-then
        assertThrows(UserAlreadyExistsException.class,
                () -> userAuthService.register(sampleRegisterDto().email("secondUser@user").username("username").build()));
    }
}
