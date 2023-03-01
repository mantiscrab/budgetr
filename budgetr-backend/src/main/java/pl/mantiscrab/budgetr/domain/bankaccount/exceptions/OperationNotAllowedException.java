package pl.mantiscrab.budgetr.domain.bankaccount.exceptions;

import org.springframework.http.HttpStatus;
import pl.mantiscrab.budgetr.BudgetrException;

public class OperationNotAllowedException extends BudgetrException {
    public OperationNotAllowedException() {
        super("Operation not allowed", HttpStatus.CONFLICT);
    }
}
