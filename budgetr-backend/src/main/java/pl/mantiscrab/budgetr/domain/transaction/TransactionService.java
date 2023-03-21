package pl.mantiscrab.budgetr.domain.transaction;

import org.apache.commons.lang3.NotImplementedException;
import org.springframework.data.domain.Page;
import pl.mantiscrab.budgetr.domain.transaction.dto.TransactionDto;

public class TransactionService {
    public Page<TransactionDto> getTransactionsByBankAccountId(Long bankAccountId){
        throw new NotImplementedException();
    }

    public TransactionDto getTransactionByBankAccountIdAndIndex(Long bankAccountId, Integer index) {
        throw new NotImplementedException();
    }

    public TransactionDto createTransaction(Long bankAccountId, TransactionDto newTransactionDto) {
        throw new NotImplementedException();
    }
}
