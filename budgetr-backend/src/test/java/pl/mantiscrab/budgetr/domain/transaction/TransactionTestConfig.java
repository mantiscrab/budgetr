package pl.mantiscrab.budgetr.domain.transaction;

import pl.mantiscrab.budgetr.domain.InMemoryRepositoriesProvider;
import pl.mantiscrab.budgetr.domain.bankaccount.BankAccountService;
import pl.mantiscrab.budgetr.domain.user.DummySignedInUserProvider;

class TransactionTestConfig {
    private final InMemoryRepositoriesProvider repositoriesProvider = new InMemoryRepositoriesProvider();

    TransactionService transactionService(BankAccountService bankAccountService) {
        return new TransactionService(bankAccountService, repositoriesProvider.transactionRepository());
    }


    BankAccountService bankAccountService(DummySignedInUserProvider userProvider) {
        return new BankAccountService(repositoriesProvider.bankAccountRepository(), userProvider);
    }
}
