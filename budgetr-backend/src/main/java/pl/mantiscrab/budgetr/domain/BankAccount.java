package pl.mantiscrab.budgetr.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Getter
class BankAccount {
    private final BankAccountId name;
    private final Money initialBalance;
    private final List<Transaction> transactions = new ArrayList<>();

    TransactionId addTransaction(Transaction transaction) {
        transactions.add(transaction);
        return new TransactionId(transactions.size() - 1);
    }

    Money getBalance() {
        if (transactions.isEmpty()) {
            return initialBalance;
        }
        Money money = transactions.stream().map(Transaction::getComparativeAmount).reduce(Money::add).orElseThrow();
        return initialBalance.add(money);
    }

    boolean isAnyTransactionAssociated(CategoryId catId) {
        for (Transaction transaction : transactions) {
            if (transaction.isAssociatedWith(catId))
                return true;
        }
        return false;
    }

    void replaceCategory(CategoryId oldCatId, CategoryId newCatId) {
        transactions.forEach(t -> t.replaceCategory(oldCatId, newCatId));
    }

    Transaction getTransaction(TransactionId transactionId) {
        return transactions.get(transactionId.id());
    }
}
