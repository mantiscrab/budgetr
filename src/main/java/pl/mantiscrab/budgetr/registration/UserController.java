package pl.mantiscrab.budgetr.registration;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import pl.mantiscrab.budgetr.registration.dto.UserDto;
import pl.mantiscrab.budgetr.registration.dto.UserRegisterDto;
import pl.mantiscrab.budgetr.registration.exception.ConstraintViolation;

import javax.validation.Valid;
import java.util.List;

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
    @GetMapping("/username")
    ResponseEntity<String> getUsername() {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(name);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    List<ConstraintViolation> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        return ex.getBindingResult().getFieldErrors()
                .stream()
                .map(fieldError -> new ConstraintViolation(fieldError.getField(), fieldError.getDefaultMessage()))
                .toList();
    }
}
