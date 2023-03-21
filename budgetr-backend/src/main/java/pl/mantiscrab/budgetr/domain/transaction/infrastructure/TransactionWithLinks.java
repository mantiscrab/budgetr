package pl.mantiscrab.budgetr.domain.transaction.infrastructure;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;
import pl.mantiscrab.budgetr.domain.transaction.dto.TransactionDto;

import static pl.mantiscrab.budgetr.domain.transaction.infrastructure.TransactionLinksProvider.transactionSelfLink;
import static pl.mantiscrab.budgetr.domain.transaction.infrastructure.TransactionLinksProvider.transactionsByBankAccountIdLink;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Relation(itemRelation = "transaction", collectionRelation = "transactions")
public class TransactionWithLinks extends RepresentationModel<TransactionWithLinks> {

    @JsonUnwrapped
    @Getter
    private TransactionDto transactionDto;

    public static TransactionWithLinks of(Long bankAccountId, TransactionDto transactionDto) {
        final TransactionWithLinks transactionWithLinks = new TransactionWithLinks(transactionDto);
        transactionWithLinks.add(
                transactionSelfLink(bankAccountId, transactionDto),
                transactionsByBankAccountIdLink(bankAccountId)
        );
        return transactionWithLinks;
    }
}
