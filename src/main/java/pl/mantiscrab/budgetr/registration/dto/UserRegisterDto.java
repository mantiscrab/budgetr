package pl.mantiscrab.budgetr.registration.dto;

import lombok.Builder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Builder
public record UserRegisterDto(
        @Email @NotBlank String email,
        @NotBlank String username,
        @NotBlank String password) {
}
