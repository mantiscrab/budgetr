package pl.mantiscrab.budgetr.domain.bankaccount.dto;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record BankAccountDto(Long id, String name, BigDecimal initialBalance) {

}
