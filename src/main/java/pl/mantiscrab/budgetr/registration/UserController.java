package pl.mantiscrab.budgetr.registration;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
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

    @ExceptionHandler(MethodArgumentNotValidException.class)
    List<ConstraintViolation> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        return ex.getBindingResult().getFieldErrors()
                .stream()
                .map(fieldError -> new ConstraintViolation(fieldError.getField(), fieldError.getDefaultMessage()))
                .toList();
    }
}
