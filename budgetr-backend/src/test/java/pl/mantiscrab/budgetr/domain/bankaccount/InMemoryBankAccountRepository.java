package pl.mantiscrab.budgetr.domain.bankaccount;

import pl.mantiscrab.budgetr.domain.user.User;

import java.util.*;
import java.util.stream.Collectors;

public class InMemoryBankAccountRepository implements BankAccountRepository {
    private final Map<Long, BankAccount> map;
    private Long index;

    public InMemoryBankAccountRepository(Map<Long, BankAccount> map) {
        this.map = map;
        this.index = 1L;
    }

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
    public void deleteByUserAndId(User user, Long id) {
        BankAccount bankAccount = map.get(id);
        if (bankAccount != null) {
            if (bankAccount.getUser().equals(user))
                map.remove(id);
        }
    }

    @Override
    public Set<BankAccount> findByUserAndName(User user, String name) {
        return map.values().parallelStream()
                .filter(bankAccount -> bankAccount.getUser().equals(user)
                        && Objects.equals(bankAccount.getName(), name))
                .collect(Collectors.toSet());
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public Iterable<BankAccount> findAll() {
        return null;
    }

    @Override
    public Iterable<BankAccount> findAllById(Iterable<Long> longs) {
        return null;
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void delete(BankAccount entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends BankAccount> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public <S extends BankAccount> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }
}
