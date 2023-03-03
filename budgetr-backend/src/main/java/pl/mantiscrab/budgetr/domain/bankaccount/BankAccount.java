package pl.mantiscrab.budgetr.domain.bankaccount;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pl.mantiscrab.budgetr.domain.user.User;

import javax.persistence.*;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter(AccessLevel.PACKAGE)
@Entity
public class BankAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToOne
    @JoinColumn(name = "username")
    private User user;
    private BigDecimal initialBalance;
}
