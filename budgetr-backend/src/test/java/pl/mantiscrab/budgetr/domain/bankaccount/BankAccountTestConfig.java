package pl.mantiscrab.budgetr.domain.bankaccount;

import pl.mantiscrab.budgetr.domain.user.SignedInUserGetter;

class BankAccountTestConfig {
    static BankAccountService bankAccountService(SignedInUserGetter userGetter) {
        BankAccountRepository repository = new InMemoryBankAccountRepository();
        return new BankAccountService(repository, userGetter);
    }
}
