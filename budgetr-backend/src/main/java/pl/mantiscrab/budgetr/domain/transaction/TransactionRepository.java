package pl.mantiscrab.budgetr.domain.transaction;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import pl.mantiscrab.budgetr.domain.transaction.dto.TransactionDto;

import java.util.Optional;

interface TransactionRepository extends CrudRepository<Transaction, Long> {
    @Query("SELECT NEW java.util.Optional pl.mantiscrab.budgetr.domain.transaction.dto.TransactionDto(INDEX(t), t.amount, t.date) " +
            "FROM BankAccount ba JOIN ba.transactions t " +
            "WHERE t.id = :id ")
    Optional<TransactionDto> getTransactionDtoById(Long id);

    @Query("SELECT NEW pl.mantiscrab.budgetr.domain.transaction.dto.TransactionDto(INDEX(t), t.amount, t.date) " +
            "FROM BankAccount ba JOIN ba.transactions t " +
            "WHERE ba.id = :id AND INDEX(t) = :index ")
    Optional<TransactionDto> findTransactionByBankAccountIdAndIndex(Long bankAccountId, Integer index);
}
