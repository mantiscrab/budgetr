package pl.mantiscrab.budgetr.domain;

import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import pl.mantiscrab.budgetr.domain.dto.BankAccountDto;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
public class BankAccountFacade {
    private final BankAccountService bankAccountService;

    @Transactional
    public BankAccountDto addBankAccount(BankAccountDto newBankAccount) {
        return bankAccountService.addBankAccount(newBankAccount);
    }

    @Transactional
    public BankAccountDto updateBankAccount(Integer index, BankAccountDto bankAccountDto) {
        return bankAccountService.updateBankAccount(index, bankAccountDto);
    }

    @Transactional
    public void deleteBankAccount(Integer index) {
        bankAccountService.deleteBankAccount(index);
    }

    public List<BankAccountDto> getAccounts() {
        return bankAccountService.getAccounts();
    }

    public Optional<BankAccountDto> getAccount(int index) {
        return bankAccountService.getAccount(index);
    }
}
