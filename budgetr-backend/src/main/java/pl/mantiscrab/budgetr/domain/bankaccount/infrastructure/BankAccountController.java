package pl.mantiscrab.budgetr.domain.bankaccount.infrastructure;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.mantiscrab.budgetr.domain.bankaccount.BankAccountService;
import pl.mantiscrab.budgetr.domain.bankaccount.BankAccounts;

@AllArgsConstructor
@CrossOrigin
@RestController
@RequestMapping("/accounts")
class BankAccountController {
    private final BankAccountService accountService;

    @GetMapping
    ResponseEntity<BankAccounts> getAccounts() {
        BankAccounts bankAccounts = new BankAccounts(accountService.getAccounts());
        return ResponseEntity.ok(bankAccounts);
    }
}
