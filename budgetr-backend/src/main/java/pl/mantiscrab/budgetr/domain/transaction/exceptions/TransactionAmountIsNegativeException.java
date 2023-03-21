package pl.mantiscrab.budgetr.domain.transaction.exceptions;

import org.springframework.http.HttpStatus;
import pl.mantiscrab.budgetr.BudgetrException;

public class TransactionAmountIsNegativeException extends BudgetrException {
    public TransactionAmountIsNegativeException() {
        super("Transaction amount must not be negative", HttpStatus.BAD_REQUEST);
    }
}
