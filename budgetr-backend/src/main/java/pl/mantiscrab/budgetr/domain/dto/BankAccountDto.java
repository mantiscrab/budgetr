package pl.mantiscrab.budgetr.domain.dto;

import pl.mantiscrab.budgetr.domain.BankAccountId;
import pl.mantiscrab.budgetr.domain.Money;

public record BankAccountDto(BankAccountId id, Money initialBalance) {

    public static BankAccountDto of(BankAccountId id, Money initialBalance) {
        return new BankAccountDto(id, initialBalance);
    }

    public static BankAccountDto of(String name, Money initialBalance) {
        return new BankAccountDto(new BankAccountId(name), initialBalance);
    }

    public static BankAccountDto of(String name, String initialBalance) {
        return new BankAccountDto(new BankAccountId(name), Money.of(initialBalance));
    }
}
