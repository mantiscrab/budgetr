package pl.mantiscrab.budgetr.domain.bankaccount;

import lombok.*;
import pl.mantiscrab.budgetr.domain.user.User;

import javax.persistence.*;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter(AccessLevel.PACKAGE)
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
    private BigDecimal initialBalance;
}