package pl.mantiscrab.budgetr.domain;

import pl.mantiscrab.budgetr.domain.dto.BankAccountDto;
import pl.mantiscrab.budgetr.domain.exceptions.OperationNotAllowedException;

class BankAccountFactory {
    static BankAccount create(BankAccountDto newBankAccountDto) {
        if (newBankAccountDto.index() != null)
            throw new OperationNotAllowedException();
        return new BankAccount(
                null,
                newBankAccountDto.name(),
                newBankAccountDto.initialBalance());
    }
}
