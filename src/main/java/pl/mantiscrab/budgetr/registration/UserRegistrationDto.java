package pl.mantiscrab.budgetr.registration;

import lombok.Data;

@Data
class UserRegistrationDto {
    private final String email;
    private final String username;
    private final String password;
}
