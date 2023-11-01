package pl.mantiscrab.budgetr.domain.exceptions;

import pl.mantiscrab.budgetr.domain.BankAccountId;

public class BankAccountNotFoundException extends RuntimeException {
    public BankAccountNotFoundException(BankAccountId bankAccountId) {
        super("Bank account with id " + bankAccountId.name() + " not found");
    }
}
