package pl.mantiscrab.budgetr.domain.bankaccount;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;
import pl.mantiscrab.budgetr.domain.bankaccount.dto.BankAccountDto;

import java.net.URI;
import java.util.List;

import static org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder.on;

@AllArgsConstructor
@RestController
class BankAccountController {
    private final BankAccountService bankAccountService;

    @GetMapping("/bank-accounts/{id}")
    ResponseEntity<BankAccountDto> getAccountById(@PathVariable Long id) {
        return ResponseEntity.of(bankAccountService.getAccount(id));
    }

    @GetMapping("/bank-accounts")
    ResponseEntity<List<BankAccountDto>> getAccounts() {
        return ResponseEntity.ok(bankAccountService.getAccounts());
    }

    @PostMapping("/bank-accounts")
    ResponseEntity<BankAccountDto> createBankAccount(@RequestBody BankAccountDto bankAccountDto) {
        BankAccountDto createdBankAccount = bankAccountService.createBankAccount(bankAccountDto);
        URI uri = getLocationUri(createdBankAccount);
        return ResponseEntity.created(uri).body(createdBankAccount);
    }

    private URI getLocationUri(BankAccountDto createdBankAccount) {
        Long id = createdBankAccount.id();
        return MvcUriComponentsBuilder.fromMethodCall(
                on(this.getClass()).getAccountById(id)).build().toUri();
    }

}