package pl.mantiscrab.budgetr.domain.infrastructure;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Service;
import pl.mantiscrab.budgetr.domain.dto.BankAccountDto;

import java.util.List;

@Service
class BankAccountWithLinksAssembler implements RepresentationModelAssembler<BankAccountDto, BankAccountWithLinks> {

    @Override
    public BankAccountWithLinks toModel(BankAccountDto bankAccountDto) {
        return BankAccountWithLinks.of(bankAccountDto.clone());
    }

    public CollectionModel<BankAccountWithLinks> toModel(List<BankAccountDto> bankAccountDtos) {
        final List<BankAccountWithLinks> bankAccountsWithLinks = bankAccountDtos.stream().map(this::toModel).toList();
        return CollectionModel.of(bankAccountsWithLinks, BankAccountLinksProvider.bankAccountsLink());
    }
}
