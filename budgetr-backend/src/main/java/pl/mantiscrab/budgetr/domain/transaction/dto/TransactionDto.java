package pl.mantiscrab.budgetr.domain.transaction.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
public record TransactionDto(Integer index, BigDecimal amount, LocalDate date) {
}
