package pl.mantiscrab.budgetr.domain.bankaccount;

import pl.mantiscrab.budgetr.InMemoryDummyCrudRepository;
import pl.mantiscrab.budgetr.domain.user.User;

import java.util.List;

class InMemoryBankAccountRepository extends InMemoryDummyCrudRepository<BankAccount, Long> implements BankAccountRepository{
    @Override
    public List<BankAccount> findByUser(User user) {
        return null;
    }
}
