package pl.mantiscrab.budgetr.domain.transaction;

import pl.mantiscrab.budgetr.domain.bankaccount.BankAccount;
import pl.mantiscrab.budgetr.domain.transaction.dto.TransactionDto;

import java.util.Map;
import java.util.Optional;

public class InMemoryTransactionRepository implements TransactionRepository{
    private final Map<Long, BankAccount> bankAccountMap;
    private final Map<Long, Transaction> transactionMap;
    private Long index;

    public InMemoryTransactionRepository(Map<Long, BankAccount> bankAccountMap, Map<Long, Transaction> transactionMap) {
        this.bankAccountMap = bankAccountMap;
        this.transactionMap = transactionMap;
        this.index = 1L;
    }
    @Override
    public Optional<TransactionDto> getTransactionDtoById(Long id) {
        final Optional<Transaction> optionalTransaction = findById(id);
        if (optionalTransaction.isEmpty()) {
            return Optional.empty();
        }
        final Transaction transaction = optionalTransaction.get();
        return bankAccountMap.values().stream()
                .filter(ba -> ba.getTransactions().contains(transaction))
                .map(ba -> ba.getTransactions().indexOf(transaction))
                .findFirst().map(index -> new TransactionDto(index, transaction.getAmount(), transaction.getDate()));
    }

    @Override
    public Optional<TransactionDto> findTransactionByBankAccountIdAndIndex(Long id, Integer index) {
        return Optional.ofNullable(bankAccountMap.get(id))
                .map(bankAccount -> bankAccount.getTransactions().get(index))
                .map(transaction -> new TransactionDto(index, transaction.getAmount(), transaction.getDate()));
    }

    @Override
    public <S extends Transaction> S save(S transaction) {
        if (transaction.getId() == null) {
            transaction.setId(index);
            index++;
        }
        transactionMap.put(transaction.getId(), transaction);
        return transaction;
    }

    @Override
    public <S extends Transaction> Iterable<S> saveAll(Iterable<S> entities) {
        return null;
    }

    @Override
    public Optional<Transaction> findById(Long id) {
        Transaction transaction = transactionMap.get(id);
        return Optional.ofNullable(transaction);
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public Iterable<Transaction> findAll() {
        return null;
    }

    @Override
    public Iterable<Transaction> findAllById(Iterable<Long> longs) {
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
    public void delete(Transaction entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends Transaction> entities) {

    }

    @Override
    public void deleteAll() {

    }
}
