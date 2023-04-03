package pl.mantiscrab.budgetr.domain.bankaccount;

import lombok.*;
import pl.mantiscrab.budgetr.domain.transaction.Transaction;
import pl.mantiscrab.budgetr.domain.user.User;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter(AccessLevel.PUBLIC)
@Entity
@Table(name = "BANK_ACCOUNT")
public class BankAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.PACKAGE)
    private Long id;
    private String name;

    @ManyToOne
    @JoinColumn(name = "USERNAME")
    private User user;
    @Digits(integer = 19, fraction = 2)
    private BigDecimal initialBalance;
    @OneToMany
    @OrderColumn
    @JoinColumn(name = "bank_account_id")
    private List<Transaction> transactions = new ArrayList<>();

    BankAccount(Long id, String name, User user, BigDecimal initialBalance) {
        this.id = id;
        this.name = name;
        this.user = user;
        this.initialBalance = initialBalance;
        this.transactions = new ArrayList<>();
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
    }
}
