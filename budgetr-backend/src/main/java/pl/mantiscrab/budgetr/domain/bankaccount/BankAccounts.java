package pl.mantiscrab.budgetr.domain.bankaccount;

import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.mantiscrab.budgetr.domain.bankaccount.dto.BankAccountDto;

import java.util.List;

@AllArgsConstructor
@Getter
public record BankAccounts(List<BankAccountDto> bankAccounts) {
}
