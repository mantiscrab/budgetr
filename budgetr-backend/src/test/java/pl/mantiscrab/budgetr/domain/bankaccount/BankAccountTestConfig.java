package pl.mantiscrab.budgetr.domain.bankaccount;

import pl.mantiscrab.budgetr.domain.user.SignedInUserProvider;

class BankAccountTestConfig {
    static BankAccountService bankAccountService(SignedInUserProvider userProvider) {
        BankAccountRepository repository = new InMemoryBankAccountRepository();
        return new BankAccountService(repository, userProvider);
    }
}
