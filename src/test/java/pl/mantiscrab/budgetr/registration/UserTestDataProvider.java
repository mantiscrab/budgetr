package pl.mantiscrab.budgetr.registration;

import pl.mantiscrab.budgetr.registration.dto.UserRegisterDto;

class UserTestDataProvider {
    public static UserRegisterDto.UserRegisterDtoBuilder sampleRegisterDto() {
        return UserRegisterDto.builder()
                .email("user@user")
                .username("username")
                .password("password");
    }
}
