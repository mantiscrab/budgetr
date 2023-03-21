package pl.mantiscrab.budgetr.domain.transaction.infrastructure;

import lombok.AllArgsConstructor;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.mantiscrab.budgetr.domain.transaction.TransactionService;
import pl.mantiscrab.budgetr.domain.transaction.dto.TransactionDto;

@AllArgsConstructor
@RestController
class TransactionController {
    private final TransactionService transactionService;

    @PostMapping("/bank-account/{bankAccountId}")
    ResponseEntity<TransactionWithLinks> createTransaction(@PathVariable Long bankAccountId, @RequestParam TransactionDto transactionDto) {
        final TransactionDto transaction = transactionService.createTransaction(bankAccountId, transactionDto);
        throw new NotImplementedException();
    }

    ResponseEntity<PagedModel<TransactionWithLinks>> getTransactionsByBankAccountId
            (Long bankAccountId, Pageable pageable, PagedResourcesAssembler<TransactionDto> assembler) {
        final Page<TransactionDto> transactions = transactionService.getTransactionsByBankAccountId(bankAccountId);
//        final PagedModel<EntityModel<TransactionWithLinks>> transactionsWithLinks = assembler.toModel(transactions);
        throw new NotImplementedException();
    }

    @GetMapping("/bank-account/{bankAccountId}/transaction/{index}")
    ResponseEntity<TransactionWithLinks> getTransactionByBankAccountIdAndIndex(@PathVariable Long bankAccountId, @PathVariable Integer index) {
        final TransactionDto transaction = transactionService.getTransactionByBankAccountIdAndIndex(bankAccountId, index);
        throw new NotImplementedException();
    }

}
