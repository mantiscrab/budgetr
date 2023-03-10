package pl.mantiscrab.budgetr.domain.bankaccount;

import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import pl.mantiscrab.budgetr.domain.bankaccount.dto.BankAccountDto;
import pl.mantiscrab.budgetr.domain.bankaccount.exceptions.BankAccountWithSameNameAlreadyExistsException;
import pl.mantiscrab.budgetr.domain.bankaccount.exceptions.OperationNotAllowedException;
import pl.mantiscrab.budgetr.domain.user.SignedInUserProvider;
import pl.mantiscrab.budgetr.domain.user.User;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@AllArgsConstructor
public class BankAccountService {
    private final BankAccountRepository accountRepository;
    private final SignedInUserProvider userProvider;

    public List<BankAccountDto> getAccounts() {
        User user = userProvider.getUser();
        return accountRepository.findByUser(user).parallelStream()
                .map(BankAccountMapper::mapBankAccountToBankAccountDto).toList();
    }

    public Optional<BankAccountDto> getAccount(Long id) {
        User user = userProvider.getUser();
        return accountRepository.findByUserAndId(user, id)
                .map(BankAccountMapper::mapBankAccountToBankAccountDto);
    }

    public BankAccountDto createBankAccount(BankAccountDto newBankAccount) {
        validateBeforeCreate(newBankAccount);
        User user = userProvider.getUser();
        BankAccount bankAccount = BankAccountMapper.mapBankAccountDtoToBankAccount(newBankAccount, user);
        BankAccount savedBankAccount = accountRepository.save(bankAccount);
        return BankAccountMapper.mapBankAccountToBankAccountDto(savedBankAccount);
    }

    @Transactional
    public BankAccountDto updateBankAccount(Long id, BankAccountDto bankAccountDto) {
        validateBeforeUpdate(id, bankAccountDto);
        User user = userProvider.getUser();
        BankAccount bankAccount = BankAccountMapper.mapBankAccountDtoToBankAccount(bankAccountDto, user);
        BankAccount savedBankAccount = accountRepository.save(bankAccount);
        return BankAccountMapper.mapBankAccountToBankAccountDto(savedBankAccount);
    }

    private void validateBeforeCreate(BankAccountDto newBankAccount) {
        if (newBankAccount.id() != null)
            throw new OperationNotAllowedException();
        User user = userProvider.getUser();
        throwExceptionIfUserAlreadyHasAccountWithName(user, newBankAccount.name());
    }

    private void validateBeforeUpdate(Long id, BankAccountDto bankAccountDto) {
        User user = userProvider.getUser();
        throwExceptionIfIdsDontMatch(id, bankAccountDto);
        throwExceptionIfUserDoesntOwnAccount(user, bankAccountDto);
        throwExceptionIfUserAlreadyHasAccountWithName(user, bankAccountDto.name());
    }

    private void throwExceptionIfUserAlreadyHasAccountWithName(User user, String name) {
        Set<BankAccount> bankAccountsByUserAndName = accountRepository.findByUserAndName(user, name);
        if (!bankAccountsByUserAndName.isEmpty()) {
            throw BankAccountWithSameNameAlreadyExistsException.withName(name);
        }
    }

    private void throwExceptionIfIdsDontMatch(Long id, BankAccountDto bankAccountDto) {
        if (!Objects.equals(id, bankAccountDto.id()))
            throw new OperationNotAllowedException();
    }

    private void throwExceptionIfUserDoesntOwnAccount(User user, BankAccountDto bankAccountDto) {
        accountRepository.findByUserAndId(user, bankAccountDto.id()).orElseThrow(OperationNotAllowedException::new);
    }

    private static class BankAccountMapper {

        private static BankAccountDto mapBankAccountToBankAccountDto(BankAccount bankAccount) {
            return new BankAccountDto(
                    bankAccount.getId(),
                    bankAccount.getName(),
                    bankAccount.getInitialBalance());
        }

        private static BankAccount mapBankAccountDtoToBankAccount(BankAccountDto bankAccountDto, User user) {
            return new BankAccount(
                    bankAccountDto.id(),
                    bankAccountDto.name(),
                    user,
                    bankAccountDto.initialBalance()
            );
        }
    }
}
