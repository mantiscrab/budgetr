package pl.mantiscrab.budgetr.domain.dto;

import lombok.Builder;
import pl.mantiscrab.budgetr.domain.Prototype;

import java.math.BigDecimal;

@Builder
public record BankAccountDto(Integer index, String name, BigDecimal initialBalance) implements Prototype {
    @Override
    public BankAccountDto clone() {
        return new BankAccountDto(index, name, initialBalance);
    }
}
