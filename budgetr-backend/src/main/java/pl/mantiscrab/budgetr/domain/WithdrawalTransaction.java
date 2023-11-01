package pl.mantiscrab.budgetr.domain;

import java.time.LocalDate;
import java.util.List;

class WithdrawalTransaction extends Transaction {
    private WithdrawalTransaction(PayeeId payeeId, Money totalAmount, LocalDate date, String description, List<TransactionComponent> parts) {
        super(payeeId, totalAmount, date, description, parts);
    }

    @Override
    public Money getComparativeAmount() {
        return amount.changeSignum();
    }

    public static WithdrawalTransaction of(PayeeId payeeId, Money totalAmount, LocalDate date, String description, List<TransactionComponent> parts) {
        return new WithdrawalTransaction(payeeId, totalAmount, date, description, parts);
    }
}