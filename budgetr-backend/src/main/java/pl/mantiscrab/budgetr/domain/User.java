package pl.mantiscrab.budgetr.domain;

import lombok.*;
import pl.mantiscrab.budgetr.domain.dto.BankAccountDto;
import pl.mantiscrab.budgetr.domain.exceptions.BankAccountWithSameNameAlreadyExistsException;
import pl.mantiscrab.budgetr.domain.exceptions.OperationNotAllowedException;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter(AccessLevel.PACKAGE)
@Entity
@Table(name = "BUDGETR_USERS")
class User {
    @Id
    @Column(nullable = false)
    @NotBlank
    private String username;

    @Column(nullable = false, unique = true)
    @Email
    @NotBlank
    private String email;

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinColumn(name = "username")
    @OrderColumn
    private List<BankAccount> bankAccounts;

    User(String username, String email) {
        this.username = username;
        this.email = email;
        this.bankAccounts = new ArrayList<>();
    }

    public void updateEmail(String email) {
        this.email = email;
    }

    int addBankAccount(BankAccountDto newBankAccountDto) {
        final BankAccount newBankAccount = BankAccountFactory.create(newBankAccountDto);
        if (userAlreadyHasAccountWithName(newBankAccount.getName())) {
            throw BankAccountWithSameNameAlreadyExistsException.withName(newBankAccount.getName());
        }
        bankAccounts.add(newBankAccount);
        return lastIndexOfBankAccount();
    }

    int updateAccount(BankAccountDto bankAccountInfo) {
        if (userAlreadyHasDifferentAccountWithName(bankAccountInfo.name(), bankAccountInfo.index())) {
            throw BankAccountWithSameNameAlreadyExistsException.withName(bankAccountInfo.name());
        }
        BankAccount account = bankAccounts.get(bankAccountInfo.index());
        account.update(bankAccountInfo);
        return bankAccountInfo.index();
    }

    private boolean userAlreadyHasAccountWithName(String name) {
        return bankAccounts.stream().anyMatch(ba -> ba.hasName(name));
    }

    private boolean userAlreadyHasDifferentAccountWithName(String newBankAccount, int index) {
        List<BankAccount> bankAccountsCopy = new ArrayList<>(bankAccounts);
        if (bankAccountsContainIndex(index)) {
            bankAccountsCopy.remove(index);
        } else
            throw new OperationNotAllowedException();
        return bankAccountsCopy.stream()
                .anyMatch(ba -> ba.hasName(newBankAccount));
    }

    private boolean bankAccountsContainIndex(final int index) {
        return index < bankAccounts.size();
    }

    public void removeAccount(Integer index) {
        if (index >= bankAccounts.size()) {
            return;
        }
        this.bankAccounts.remove(index.intValue());
    }

    private int lastIndexOfBankAccount() {
        return bankAccounts.size() - 1;
    }

    private static class BankAccountFactory {
        static BankAccount create(BankAccountDto newBankAccountDto) {
            if (newBankAccountDto.index() != null)
                throw new OperationNotAllowedException();
            return new BankAccount(
                    null,
                    newBankAccountDto.name(),
                    newBankAccountDto.initialBalance());
        }
    }
}
