package pl.mantiscrab.budgetr.domain;

import org.apache.commons.lang3.NotImplementedException;
import pl.mantiscrab.budgetr.domain.dto.BankAccountDto;

import java.util.*;

class InMemoryUserRepository implements UserRepository {

    private final Map<String, User> userMap = new HashMap<>();

    @Override
    public Optional<User> findByUsername(String username) {
        return Optional.ofNullable(userMap.get(username));
    }

    @Override
    public List<BankAccountDto> findByUser(User user) {
        List<BankAccount> bankAccounts = userMap.get(user.getUsername()).getBankAccounts();
        List<BankAccountDto> bankAccountDtos = new ArrayList<>();
        for (int index = 0; index < bankAccounts.size(); index++) {
            BankAccount currentBankAccount = bankAccounts.get(index);
            BankAccountDto bankAccountDto = new BankAccountDto(index,
                    currentBankAccount.getName(),
                    currentBankAccount.getInitialBalance());
            bankAccountDtos.add(bankAccountDto);
        }
        return bankAccountDtos;
    }

    @Override
    public BankAccountDto findByUserAndIndex(User user, Integer index) {
        User foundUser = userMap.get(user.getUsername());
        List<BankAccount> bankAccounts = foundUser.getBankAccounts();
        if (bankAccounts.size() <= index) {
            return null;
        }
        BankAccount bankAccount = bankAccounts.get(index);
        return new BankAccountDto(index, bankAccount.getName(), bankAccount.getInitialBalance());
    }

    @Override
    public <S extends User> S save(S entity) {
        userMap.put(entity.getUsername(), entity);
        return entity;
    }

    @Override
    public <S extends User> Iterable<S> saveAll(Iterable<S> entities) {
        throw new NotImplementedException();
    }

    @Override
    public Optional<User> findById(Long aLong) {
        throw new NotImplementedException();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public Iterable<User> findAll() {
        throw new NotImplementedException();
    }

    @Override
    public Iterable<User> findAllById(Iterable<Long> longs) {
        throw new NotImplementedException();
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void delete(User entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends User> entities) {

    }

    @Override
    public void deleteAll() {

    }
}
