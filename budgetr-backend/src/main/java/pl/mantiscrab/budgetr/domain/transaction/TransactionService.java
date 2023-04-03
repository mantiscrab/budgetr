package pl.mantiscrab.budgetr.domain.transaction;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;
import pl.mantiscrab.budgetr.domain.bankaccount.BankAccount;
import pl.mantiscrab.budgetr.domain.bankaccount.BankAccountService;
import pl.mantiscrab.budgetr.domain.exceptions.OperationNotAllowedException;
import pl.mantiscrab.budgetr.domain.transaction.dto.TransactionDto;
import pl.mantiscrab.budgetr.domain.transaction.exceptions.BankAccountDoesntExistException;
import pl.mantiscrab.budgetr.domain.transaction.exceptions.TransactionAmountIsNegativeException;

import java.util.Optional;

@AllArgsConstructor
public class TransactionService {
    private final BankAccountService bankAccountService;
    private final TransactionRepository transactionRepository;

    @Transactional
    public TransactionDto createTransaction(Long bankAccountId, TransactionDto newTransactionDto) {
        if (newTransactionDto.index() != null) {
            throw new OperationNotAllowedException();
        }
        if (newTransactionDto.amount().signum() < 0 ){
            throw new TransactionAmountIsNegativeException();
        }
        BankAccount bankAccount = bankAccountService.getSignedInUsersBankAccount(bankAccountId)
                .orElseThrow(() -> {
                    throw new BankAccountDoesntExistException(bankAccountId);
                });
        final Transaction newTransaction = TransactionMapper.mapTransactionDtoToTransaction(newTransactionDto);
        final Transaction savedTransaction = transactionRepository.save(newTransaction);
        bankAccount.addTransaction(newTransaction);
        return transactionRepository.getTransactionDtoById(savedTransaction.getId()).orElseThrow();
    }

    public Page<TransactionDto> getTransactionsByBankAccountId(Long bankAccountId) {
        throw new NotImplementedException();
    }

    public Optional<TransactionDto> getTransactionByBankAccountIdAndIndex(Long bankAccountId, Integer index) {
        return transactionRepository.findTransactionByBankAccountIdAndIndex(bankAccountId, index);
    }

    private static class TransactionMapper {
        private static Transaction mapTransactionDtoToTransaction(TransactionDto newTransactionDto) {
            return new Transaction(null, newTransactionDto.date(), newTransactionDto.amount());
        }
    }
}
