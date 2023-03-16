package pl.mantiscrab.budgetr.domain.bankaccount.infrastructure;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Service;
import pl.mantiscrab.budgetr.domain.bankaccount.dto.BankAccountDto;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
class BankAccountWithLinksAssembler implements RepresentationModelAssembler<BankAccountDto, BankAccountWithLinks> {

    @Override
    public BankAccountWithLinks toModel(BankAccountDto bankAccountDto) {
        BankAccountWithLinks bankAccountWithLinks = BankAccountWithLinks.of(bankAccountDto);
        bankAccountWithLinks.add(
                selfLink(bankAccountDto),
                bankAccountsLink()
        );
        return bankAccountWithLinks;
    }

    private static Link bankAccountsLink() {
        return linkTo(methodOn(BankAccountController.class).getAccounts()).withRel("bankAccounts");
    }

    private static Link selfLink(BankAccountDto bankAccountDto) {
        return linkTo(methodOn(BankAccountController.class).getAccountById(bankAccountDto.id())).withSelfRel();
    }
}
