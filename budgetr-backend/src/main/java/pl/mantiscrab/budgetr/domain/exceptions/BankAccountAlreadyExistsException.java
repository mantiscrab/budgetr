package pl.mantiscrab.budgetr.domain.exceptions;

import pl.mantiscrab.budgetr.domain.BankAccountId;

public class BankAccountAlreadyExistsException extends RuntimeException {
    public BankAccountAlreadyExistsException(BankAccountId id) {
        super("Bank account with name " + id.name() + " already exists");
    }
}
