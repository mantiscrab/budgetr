package pl.mantiscrab.budgetr.domain;

import lombok.*;
import pl.mantiscrab.budgetr.domain.dto.BankAccountDto;

import javax.persistence.*;
import javax.validation.constraints.Digits;
import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter(AccessLevel.PACKAGE)
@Entity
@Table(name = "BANK_ACCOUNT")
class BankAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Setter(AccessLevel.PACKAGE)
    private Long id;
    private String name;

    @Digits(integer = 19, fraction = 2)
    private BigDecimal initialBalance;

    public boolean hasName(String name) {
        return this.name.equals(name);
    }

    public void update(BankAccountDto bankAccountInfo) {
        this.initialBalance = bankAccountInfo.initialBalance();
        this.name = bankAccountInfo.name();
    }
}
