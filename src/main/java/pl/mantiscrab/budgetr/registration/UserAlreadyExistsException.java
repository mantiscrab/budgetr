package pl.mantiscrab.budgetr.registration;

import org.springframework.http.HttpStatus;
import pl.mantiscrab.budgetr.BudgetrException;

class UserAlreadyExistsException extends BudgetrException {
    private UserAlreadyExistsException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }

    static UserAlreadyExistsException withEmail(String email) {
        String message = "User with email \"" + email + "\" already exist";
        return new UserAlreadyExistsException(message, HttpStatus.CONFLICT);
    }

    static UserAlreadyExistsException withUsername(String username) {
        String message = "User with username \"" + username + "\" already exist";
        return new UserAlreadyExistsException(message, HttpStatus.CONFLICT);
    }
}
