package pl.mantiscrab.budgetr.domain;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

class BankAccountRepository {
    private final Map<BankAccountId, BankAccount> bankAccounts = new HashMap<>();


    void save(BankAccount bankAccount) {
        bankAccounts.put(bankAccount.getName(), bankAccount);
    }

    Optional<BankAccount> findBankAccount(BankAccountId bankAccountId) {
        return Optional.ofNullable(bankAccounts.get(bankAccountId));
    }

    List<BankAccount> findAll() {
        return bankAccounts.values().stream().toList();
    }
}
