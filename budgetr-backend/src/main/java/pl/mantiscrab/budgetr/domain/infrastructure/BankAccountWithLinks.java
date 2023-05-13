package pl.mantiscrab.budgetr.domain.infrastructure;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;
import pl.mantiscrab.budgetr.domain.dto.BankAccountDto;

import java.math.BigDecimal;
import java.net.URI;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Relation(collectionRelation = "bankAccounts")
public class BankAccountWithLinks extends RepresentationModel<BankAccountWithLinks> {

    private final BankAccountDto bankAccount;

    /**
     * Constructor needed for proper deserialization in tests
     **/
    private BankAccountWithLinks(BankAccountDto bankAccount, Iterable<Link> initialLinks) {
        this.bankAccount = bankAccount;
    }

    static BankAccountWithLinks of(BankAccountDto bankAccountDto) {
        final BankAccountWithLinks bankAccountWithLinks = new BankAccountWithLinks(bankAccountDto);
        bankAccountWithLinks.add(
                BankAccountLinksProvider.bankAccountSelfLink(bankAccountDto.index()),
                BankAccountLinksProvider.bankAccountsLink()
        );
        return bankAccountWithLinks;
    }

    URI getSelfUri() {
        return this.getLink(IanaLinkRelations.SELF).orElseThrow().toUri();
    }

    public Integer id() {
        return bankAccount.index();
    }

    public String name() {
        return bankAccount.name();
    }

    public BigDecimal initialBalance() {
        return bankAccount.initialBalance();
    }
}
