package pl.mantiscrab.budgetr.domain.bankaccount;

import pl.mantiscrab.budgetr.domain.bankaccount.dto.BankAccountDto;

import java.util.List;

public record BankAccounts(List<BankAccountDto> bankAccounts) {
}
