package pl.mantiscrab.budgetr.domain.bankaccount.dto;

import java.math.BigDecimal;

public record BankAccountDto(Long id, String name, BigDecimal initialBalance) {

}
