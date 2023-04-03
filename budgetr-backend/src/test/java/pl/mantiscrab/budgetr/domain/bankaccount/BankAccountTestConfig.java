package pl.mantiscrab.budgetr.domain.bankaccount;

import pl.mantiscrab.budgetr.domain.InMemoryRepositoriesProvider;
import pl.mantiscrab.budgetr.domain.user.SignedInUserProvider;

public class BankAccountTestConfig {

    private final InMemoryRepositoriesProvider repositoriesProvider = new InMemoryRepositoriesProvider();

    public BankAccountService bankAccountService(SignedInUserProvider userProvider) {
        BankAccountRepository repository = repositoriesProvider.bankAccountRepository();
        return new BankAccountService(repository, userProvider);
    }
}
