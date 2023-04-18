package pl.mantiscrab.budgetr.domain;

class BankAccountTestConfig {
    static BankAccountService bankAccountService(SignedInUserProvider userProvider) {
        BankAccountRepository repository = new InMemoryBankAccountRepository();
        return new BankAccountService(repository, userProvider);
    }
}
