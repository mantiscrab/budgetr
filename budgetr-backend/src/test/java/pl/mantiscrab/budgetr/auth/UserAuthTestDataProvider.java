package pl.mantiscrab.budgetr.auth;

import pl.mantiscrab.budgetr.auth.dto.UserRegisterDto;

public class UserAuthTestDataProvider {
    public static UserRegisterDto.UserRegisterDtoBuilder sampleRegisterDto() {
        return UserRegisterDto.builder()
                .email("user@user")
                .username("username")
                .password("password");
    }
}
