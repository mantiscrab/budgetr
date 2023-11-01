package pl.mantiscrab.budgetr.domain.exceptions;

import pl.mantiscrab.budgetr.domain.Money;

public class MoneyAmountInconsistencyException extends RuntimeException {
    public MoneyAmountInconsistencyException(Money sum, Money totalAmount) {
        super(String.format("""
                Transaction's total amount must be a sum of individual transaction components:
                total :           %s
                components sum:   %s""",  totalAmount, sum));
    }
}
