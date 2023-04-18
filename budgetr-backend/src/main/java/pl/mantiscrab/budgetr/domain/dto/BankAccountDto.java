package pl.mantiscrab.budgetr.domain.dto;

import lombok.Builder;
import pl.mantiscrab.budgetr.domain.Prototype;

import java.math.BigDecimal;

@Builder
public record BankAccountDto(Long id, String name, BigDecimal initialBalance) implements Prototype {
    @Override
    public BankAccountDto clone() {
        return new BankAccountDto(id, name, initialBalance);
    }
}
