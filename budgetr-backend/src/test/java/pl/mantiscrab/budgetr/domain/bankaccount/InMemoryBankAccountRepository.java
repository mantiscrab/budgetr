package pl.mantiscrab.budgetr.domain.bankaccount;

import pl.mantiscrab.budgetr.InMemoryDummyCrudRepository;
import pl.mantiscrab.budgetr.domain.user.User;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

class InMemoryBankAccountRepository extends InMemoryDummyCrudRepository<BankAccount, Long> implements BankAccountRepository {
    @Override
    public Optional<BankAccount> findById(Long id) {
        BankAccount bankAccount = map.get(id);
        return Optional.ofNullable(bankAccount);
    }

    @Override
    public <S extends BankAccount> S save(S bankAccount) {
        if (bankAccount.getId() == null) {
            bankAccount.setId(index);
            index++;
        }
        map.put(bankAccount.getId(), bankAccount);
        return bankAccount;
    }

    @Override
    public List<BankAccount> findByUser(User user) {
        return map.values().parallelStream()
                .filter(bankAccount -> bankAccount.getUser().equals(user))
                .toList();
    }

    @Override
    public Optional<BankAccount> findByUserAndId(User user, Long id) {
        return map.values().parallelStream()
                .filter(bankAccount -> bankAccount.getUser().equals(user)
                        && Objects.equals(bankAccount.getId(), id))
                .findFirst();
    }

    @Override
    public Optional<BankAccount> findByUserAndName(User user, String name) {
        return map.values().parallelStream()
                .filter(bankAccount -> bankAccount.getUser().equals(user)
                        && Objects.equals(bankAccount.getName(), name))
                .findFirst();
    }
}
