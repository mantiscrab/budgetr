package pl.mantiscrab.budgetr.domain.bankaccount.infrastructure;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;
import pl.mantiscrab.budgetr.domain.bankaccount.dto.BankAccountDto;

import java.net.URI;

import static pl.mantiscrab.budgetr.domain.bankaccount.infrastructure.BankAccountLinksProvider.bankAccountByIdLink;
import static pl.mantiscrab.budgetr.domain.bankaccount.infrastructure.BankAccountLinksProvider.bankAccountsLink;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Relation(collectionRelation = "bankAccounts")
class BankAccountWithLinks extends RepresentationModel<BankAccountWithLinks> {

    private final BankAccountDto bankAccount;

    static BankAccountWithLinks of(BankAccountDto bankAccountDto) {
        final BankAccountWithLinks bankAccountWithLinks = new BankAccountWithLinks(bankAccountDto);
        bankAccountWithLinks.add(
                bankAccountByIdLink(bankAccountDto.id()),
                bankAccountsLink());
        return bankAccountWithLinks;
    }

    URI getSelfUri() {
        return this.getLink(IanaLinkRelations.SELF).orElseThrow().toUri();
    }
}
