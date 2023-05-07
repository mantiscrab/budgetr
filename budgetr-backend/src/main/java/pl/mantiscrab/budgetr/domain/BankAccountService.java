package pl.mantiscrab.budgetr.domain;

import lombok.AllArgsConstructor;
import pl.mantiscrab.budgetr.domain.dto.BankAccountDto;
import pl.mantiscrab.budgetr.domain.exceptions.OperationNotAllowedException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@AllArgsConstructor
class BankAccountService {
    private final SignedInUserProvider userProvider;
    private final UserRepository userRepository;

    BankAccountDto addBankAccount(BankAccountDto newBankAccount) {
        User user = userProvider.getUser();
        int bankAccountIndex = user.addBankAccount(newBankAccount);
        userRepository.save(user);
        return userRepository.findByUserAndIndex(user, bankAccountIndex);
    }

    public BankAccountDto updateBankAccount(Integer index, BankAccountDto bankAccountDto) {
        throwExceptionIfIdsDontMatch(index, bankAccountDto);
        User user = userProvider.getUser();
        int bankAccountIndex = user.updateAccount(bankAccountDto);
        userRepository.save(user);
        return userRepository.findByUserAndIndex(user, bankAccountIndex);
    }

    void deleteBankAccount(Integer index) {
        User user = userProvider.getUser();
        user.removeAccount(index);
        userRepository.save(user);
    }

    private void throwExceptionIfIdsDontMatch(Integer id, BankAccountDto bankAccountDto) {
        if (!Objects.equals(id, bankAccountDto.index()))
            throw new OperationNotAllowedException();
    }

    public List<BankAccountDto> getAccounts() {
        return userRepository.findByUser(userProvider.getUser());
    }

    public Optional<BankAccountDto> getAccount(int index) {
        return Optional.ofNullable(userRepository.findByUserAndIndex(userProvider.getUser(), index));
    }
}
