package pl.mantiscrab.budgetr.domain.bankaccount.infrastructure;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;
import pl.mantiscrab.budgetr.domain.bankaccount.dto.BankAccountDto;

@AllArgsConstructor
@Getter
class BankAccountWithLinks extends RepresentationModel<BankAccountWithLinks> {

    @JsonProperty("bankAccount")
    private final BankAccountDto bankAccountDto;

    static BankAccountWithLinks of(BankAccountDto bankAccountDto){
        return new BankAccountWithLinks(bankAccountDto);
    }
}
