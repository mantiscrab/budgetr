package pl.mantiscrab.budgetr.domain.dto;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record BankAccountDto(Integer index, String name, BigDecimal initialBalance) {
}
