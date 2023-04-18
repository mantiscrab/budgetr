package pl.mantiscrab.budgetr.domain.exceptions;

import org.springframework.http.HttpStatus;
import pl.mantiscrab.budgetr.BudgetrException;

public class BudgetrUserNotAuthenticatedException extends BudgetrException {
    public BudgetrUserNotAuthenticatedException() {
        super("User is not authenticated", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
