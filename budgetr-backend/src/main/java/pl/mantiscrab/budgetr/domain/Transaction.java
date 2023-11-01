package pl.mantiscrab.budgetr.domain;

import lombok.Getter;
import pl.mantiscrab.budgetr.domain.exceptions.MoneyAmountInconsistencyException;

import java.time.LocalDate;
import java.util.List;

@Getter
abstract class Transaction {
    protected final PayeeId payeeId;
    protected final Money amount;
    protected final LocalDate date;
    protected final String description;
    protected final List<TransactionComponent> components;

    protected Transaction(PayeeId payeeId, Money amount, LocalDate date, String description, List<TransactionComponent> components) {
        this.payeeId = payeeId;
        this.amount = amount;
        this.date = date;
        this.description = description;
        this.components = components;
        validate(components, amount);
    }

    private static void validate(List<TransactionComponent> parts, Money totalAmount) {
        Money sum = parts.stream()
                .map(TransactionComponent::getAmount)
                .reduce(Money::add)
                .orElseThrow();
        if (!sum.equals(totalAmount)) {
            throw new MoneyAmountInconsistencyException(sum, totalAmount);
        }
    }

    public abstract Money getComparativeAmount();

    boolean isAssociatedWith(CategoryId catId) {
        for (TransactionComponent component : components) {
            if (component.isAssociatedWith(catId))
                return true;
        }
        return false;
    }

    void replaceCategory(CategoryId oldCatId, CategoryId newCatId) {
        components.forEach(c -> c.replaceCategory(oldCatId, newCatId));
    }
}
