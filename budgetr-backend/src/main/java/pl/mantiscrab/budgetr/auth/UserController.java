package pl.mantiscrab.budgetr.auth;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.mantiscrab.budgetr.auth.dto.UserDto;
import pl.mantiscrab.budgetr.auth.dto.UserRegisterDto;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
class UserController {
    private final UserAuthService service;

    @PostMapping("/register")
    ResponseEntity<UserDto> register(@Valid @RequestBody UserRegisterDto userRegisterDto) {
        UserDto userDto = service.register(userRegisterDto);
        return ResponseEntity.ok().body(userDto);
    }
}
