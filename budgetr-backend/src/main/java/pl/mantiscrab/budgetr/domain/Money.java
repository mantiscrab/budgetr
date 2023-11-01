package pl.mantiscrab.budgetr.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class Money {
    private final BigDecimal amount;

    public static Money of(String value) {
        return new Money(new BigDecimal(value));
    }

    public Money add(Money toAdd) {
        BigDecimal sum = this.amount.add(toAdd.amount);
        return new Money(sum);
    }

    Money changeSignum() {
        return new Money(amount.negate());
    }

    @Override
    public String toString() {
        return "money= " + amount;
    }
}
