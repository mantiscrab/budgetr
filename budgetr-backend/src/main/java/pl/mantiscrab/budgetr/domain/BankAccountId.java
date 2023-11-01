package pl.mantiscrab.budgetr.domain;

public record BankAccountId(String name) {
    static BankAccountId of(String name) {
        return new BankAccountId(name);
    }
}
