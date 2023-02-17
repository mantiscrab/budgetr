package pl.mantiscrab.budgetr.auth;

import pl.mantiscrab.budgetr.auth.dto.UserRegisterDto;

class UserTestDataProvider {
    public static UserRegisterDto.UserRegisterDtoBuilder sampleRegisterDto() {
        return UserRegisterDto.builder()
                .email("user@user")
                .username("username")
                .password("password");
    }
}
