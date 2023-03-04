package pl.mantiscrab.budgetr.domain.bankaccount;

import lombok.AllArgsConstructor;
import pl.mantiscrab.budgetr.domain.bankaccount.dto.BankAccountDto;
import pl.mantiscrab.budgetr.domain.bankaccount.exceptions.BankAccountWithSameNameAlreadyExistsException;
import pl.mantiscrab.budgetr.domain.bankaccount.exceptions.OperationNotAllowedException;
import pl.mantiscrab.budgetr.domain.user.SignedInUserGetter;
import pl.mantiscrab.budgetr.domain.user.User;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class BankAccountService {
    private final BankAccountRepository accountRepository;
    private final SignedInUserGetter userGetter;

    public List<BankAccountDto> getAccounts() {
        User user = userGetter.getUser();
        return accountRepository.findByUser(user).parallelStream()
                .map(this::mapBankAccountToBankAccountDto).toList();
    }

    public Optional<BankAccountDto> getAccount(Long id) {
        User user = userGetter.getUser();
        return accountRepository.findByUserAndId(user, id)
                .map(this::mapBankAccountToBankAccountDto);
    }

    public BankAccountDto createBankAccount(BankAccountDto newBankAccount) {
        if (newBankAccount.id() != null) {
            throw new OperationNotAllowedException();
        }
        String newAccountName = newBankAccount.name();
        User user = userGetter.getUser();
        accountRepository.findByUserAndName(user, newAccountName)
                .ifPresent(bankAccount -> {
                    throw BankAccountWithSameNameAlreadyExistsException.withName(newBankAccount.name());
                });
        BankAccount bankAccount = mapBankAccountDtoToBankAccount(newBankAccount, user);
        BankAccount savedBankAccount = accountRepository.save(bankAccount);
        return mapBankAccountToBankAccountDto(savedBankAccount);
    }

    private BankAccountDto mapBankAccountToBankAccountDto(BankAccount bankAccount) {
        return new BankAccountDto(
                bankAccount.getId(),
                bankAccount.getName(),
                bankAccount.getInitialBalance());
    }

    private BankAccount mapBankAccountDtoToBankAccount(BankAccountDto bankAccountDto, User user) {
        return new BankAccount(
                bankAccountDto.id(),
                bankAccountDto.name(),
                user,
                bankAccountDto.initialBalance()
        );
    }
}
