package pl.mantiscrab.budgetr.registration;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
class UserController {
    private final UserService service;

    @PostMapping("/register")
    ResponseEntity<UserDto> register(@RequestBody UserRegisterDto registrationDto) {
        UserDto userDto = service.register(registrationDto);
        return ResponseEntity.ok().body(userDto);
    }
}
