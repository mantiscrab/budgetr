package pl.mantiscrab.budgetr.domain.infrastructure;

import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.mantiscrab.budgetr.domain.dto.BankAccountDto;
import pl.mantiscrab.budgetr.domain.BankAccountService;

import java.net.URI;
import java.util.List;

@AllArgsConstructor
@RestController
public class BankAccountController {
    private final BankAccountService bankAccountService;
    private final BankAccountWithLinksAssembler assembler;

    @GetMapping("/bank-account/{id}")
    public ResponseEntity<BankAccountWithLinks> getAccountById(@PathVariable Long id) {
        return ResponseEntity.of(bankAccountService
                .getAccount(id)
                .map(assembler::toModel));
    }

    @GetMapping("/bank-accounts")
    public ResponseEntity<CollectionModel<BankAccountWithLinks>> getAccounts() {
        final List<BankAccountDto> accounts = bankAccountService.getAccounts();
        final CollectionModel<BankAccountWithLinks> bankAccountsWithLinks =  assembler.toModel(accounts);
        return ResponseEntity.ok(bankAccountsWithLinks);
    }

    @PostMapping("/bank-accounts")
    public ResponseEntity<BankAccountWithLinks> createBankAccount(@RequestBody BankAccountDto bankAccountDto) {
        BankAccountDto createdBankAccount = bankAccountService.createBankAccount(bankAccountDto);
        final BankAccountWithLinks bankAccountWithLinks = assembler.toModel(createdBankAccount);
        final URI selfUri = bankAccountWithLinks.getSelfUri();
        return ResponseEntity.created(selfUri).body(bankAccountWithLinks);
    }

    @PutMapping("/bank-account/{id}")
    public ResponseEntity<BankAccountWithLinks> updateBankAccount(@PathVariable Long id, @RequestBody BankAccountDto bankAccountDto) {
        final BankAccountDto updatedBankAccount = bankAccountService.updateBankAccount(id, bankAccountDto);
        final BankAccountWithLinks updatedBankAccountWithLinks = assembler.toModel(updatedBankAccount);
        return ResponseEntity.ok(updatedBankAccountWithLinks);
    }

    @DeleteMapping("/bank-account/{id}")
    public ResponseEntity<Void> deleteBankAccount(@PathVariable Long id) {
        bankAccountService.deleteBankAccount(id);
        return ResponseEntity.noContent().build();
    }
}
