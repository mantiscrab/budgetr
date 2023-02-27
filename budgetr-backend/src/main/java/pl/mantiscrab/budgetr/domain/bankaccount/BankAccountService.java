package pl.mantiscrab.budgetr.domain.bankaccount;

import lombok.AllArgsConstructor;
import pl.mantiscrab.budgetr.domain.bankaccount.dto.BankAccountDto;
import pl.mantiscrab.budgetr.domain.user.SignedInUserGetter;
import pl.mantiscrab.budgetr.domain.user.User;

import java.util.List;

@AllArgsConstructor
public class BankAccountService {
    private final BankAccountRepository accountRepository;
    private final SignedInUserGetter userGetter;

    public List<BankAccountDto> getAccounts() {
        User user = userGetter.getUser();
        return accountRepository.findByUser(user).parallelStream()
                .map(bankAccount -> new BankAccountDto(
                        bankAccount.getId(),
                        bankAccount.getName(),
                        bankAccount.getInitialBalance())).toList();
    }

    public BankAccountDto createBankAccount(BankAccountDto newBankAccount) {
        return null;
    }

    public BankAccountDto updateBankAccount(BankAccountDto newBankAccount) {
        return null;
    }

    public void deleteBankAccount(Long id) {

    }
}
