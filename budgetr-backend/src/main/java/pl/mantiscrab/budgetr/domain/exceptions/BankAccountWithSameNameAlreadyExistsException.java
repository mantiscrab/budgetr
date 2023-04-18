package pl.mantiscrab.budgetr.domain.exceptions;

import org.springframework.http.HttpStatus;
import pl.mantiscrab.budgetr.BudgetrException;

public class BankAccountWithSameNameAlreadyExistsException extends BudgetrException {
    private BankAccountWithSameNameAlreadyExistsException(String message) {
        super(message, HttpStatus.CONFLICT);
    }

    public static BankAccountWithSameNameAlreadyExistsException withName(String bankAccountName) {
        return new BankAccountWithSameNameAlreadyExistsException(
                "Bank Account with name " + bankAccountName + " already exists");
    }
}
