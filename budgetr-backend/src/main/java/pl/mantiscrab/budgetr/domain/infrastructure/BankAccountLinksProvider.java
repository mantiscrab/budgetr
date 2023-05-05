package pl.mantiscrab.budgetr.domain.infrastructure;

import org.springframework.hateoas.Link;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

class BankAccountLinksProvider {

    static Link bankAccountsLink() {
        return linkTo(methodOn(BankAccountController.class).getAccounts()).withRel("bankAccounts");
    }

    static Link bankAccountSelfLink(int index) {
        return linkTo(methodOn(BankAccountController.class).getAccountByIndex(index)).withSelfRel();
    }
}
