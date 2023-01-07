package pl.mantiscrab.budgetr.registration;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
class RegistrationController {
    private final RegistrationFacade facade;

    @PostMapping("/register")
    ResponseEntity<UserDto> register(@RequestBody UserRegistrationDto registrationDto) {
        UserDto userDto = facade.register(registrationDto);
        return ResponseEntity.ok().body(userDto);
    }
}
