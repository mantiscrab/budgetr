package pl.mantiscrab.budgetr.domain;

import pl.mantiscrab.budgetr.domain.bankaccount.BankAccount;
import pl.mantiscrab.budgetr.domain.bankaccount.InMemoryBankAccountRepository;
import pl.mantiscrab.budgetr.domain.transaction.InMemoryTransactionRepository;
import pl.mantiscrab.budgetr.domain.transaction.Transaction;

import java.util.HashMap;
import java.util.Map;

public class InMemoryRepositoriesProvider {
    private final Map<Long, BankAccount> bankAccountMap;
    private final Map<Long, Transaction> transactionMap;
    private final InMemoryBankAccountRepository bankAccountRepository;
    private final InMemoryTransactionRepository transactionRepository;

    public InMemoryRepositoriesProvider() {
        this.bankAccountMap = new HashMap<>();
        this.transactionMap = new HashMap<>();
        bankAccountRepository = new InMemoryBankAccountRepository(bankAccountMap);
        transactionRepository = new InMemoryTransactionRepository(bankAccountMap, transactionMap);
    }

    public InMemoryBankAccountRepository bankAccountRepository() {
        return bankAccountRepository;
    }

    public InMemoryTransactionRepository transactionRepository() {
        return transactionRepository;
    }
}
