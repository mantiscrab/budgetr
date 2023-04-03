package pl.mantiscrab.budgetr.domain.transaction.exceptions;

import org.springframework.http.HttpStatus;
import pl.mantiscrab.budgetr.BudgetrException;

public class BankAccountDoesntExistException extends BudgetrException {
    public BankAccountDoesntExistException(Long bankAccountId) {
        super("Bank account with id " + bankAccountId + " doesn't exist", HttpStatus.BAD_REQUEST);
    }
}
