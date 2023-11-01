package pl.mantiscrab.budgetr.domain;

import java.time.LocalDate;
import java.util.List;

class DepositTransaction extends Transaction {
    private DepositTransaction(PayeeId payeeId, Money totalAmount, LocalDate date, String description, List<TransactionComponent> parts) {
        super(payeeId, totalAmount, date, description, parts);
    }

    @Override
    public Money getComparativeAmount() {
        return amount;
    }

    public static DepositTransaction of(PayeeId payeeId, Money totalAmount, LocalDate date, String description, List<TransactionComponent> parts) {
        return new DepositTransaction(payeeId, totalAmount, date, description, parts);
    }
}
