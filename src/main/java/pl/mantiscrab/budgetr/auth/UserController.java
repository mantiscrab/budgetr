package pl.mantiscrab.budgetr.auth;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.mantiscrab.budgetr.auth.dto.UserDto;
import pl.mantiscrab.budgetr.auth.dto.UserRegisterDto;

import javax.validation.Valid;

@AllArgsConstructor
@RestController
class UserController {
    private final UserService service;

    @PostMapping("/register")
    ResponseEntity<UserDto> register(@Valid @RequestBody UserRegisterDto userRegisterDto) {
        UserDto userDto = service.register(userRegisterDto);
        return ResponseEntity.ok().body(userDto);
    }

    //    TODO: replace this endpoint in tests with one related
    //    TODO: to business logic and remove
    @GetMapping("/user")
    ResponseEntity<String> getUsername() {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(name);
    }
}
