package pl.mantiscrab.budgetr.registration.dto;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public record UserRegisterDto(
        @Email @NotBlank String email,
        @NotBlank String username,
        @NotBlank String password) {
}
