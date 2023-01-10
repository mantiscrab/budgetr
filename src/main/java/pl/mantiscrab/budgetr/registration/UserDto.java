package pl.mantiscrab.budgetr.registration;

import lombok.Builder;

@Builder
record UserDto(String email, String username) {
}
