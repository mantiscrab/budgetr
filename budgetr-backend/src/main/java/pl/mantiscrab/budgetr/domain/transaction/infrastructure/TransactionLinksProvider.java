package pl.mantiscrab.budgetr.domain.transaction.infrastructure;

import org.springframework.hateoas.Link;
import pl.mantiscrab.budgetr.domain.transaction.dto.TransactionDto;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

class TransactionLinksProvider {
    public static Link transactionSelfLink(Long bankAccountId, TransactionDto transactionDto) {
        return linkTo(methodOn(TransactionController.class)
                .getTransactionByBankAccountIdAndIndex(bankAccountId, transactionDto.index()))
                .withSelfRel();
    }

    public static Link transactionsByBankAccountIdLink(Long bankAccountId) {
        return linkTo(methodOn(TransactionController.class)
                .getTransactionsByBankAccountId(bankAccountId, null, null))
                .withRel("Transactions");
    }
}
