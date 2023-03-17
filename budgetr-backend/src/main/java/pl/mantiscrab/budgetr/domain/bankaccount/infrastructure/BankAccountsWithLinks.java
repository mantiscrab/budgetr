package pl.mantiscrab.budgetr.domain.bankaccount.infrastructure;

import org.springframework.hateoas.CollectionModel;

import java.util.List;

class BankAccountsWithLinks extends CollectionModel<BankAccountWithLinks> {
    List<BankAccountWithLinks> extract() {
        return super.getContent().stream().toList();
    }
}
