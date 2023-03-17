package pl.mantiscrab.budgetr.domain.bankaccount.infrastructure;

import org.springframework.hateoas.Link;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

class BankAccountLinksProvider {

    static Link bankAccountsLink() {
        return linkTo(methodOn(BankAccountController.class).getAccounts()).withRel("bankAccounts");
    }

    static Link bankAccountSelfLink(Long id) {
        return linkTo(methodOn(BankAccountController.class).getAccountById(id)).withSelfRel();
    }
}
