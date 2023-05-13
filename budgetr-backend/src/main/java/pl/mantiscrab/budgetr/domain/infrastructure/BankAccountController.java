package pl.mantiscrab.budgetr.domain.infrastructure;

import lombok.AllArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.mantiscrab.budgetr.domain.BankAccountFacade;
import pl.mantiscrab.budgetr.domain.BankAccountQueryFacade;
import pl.mantiscrab.budgetr.domain.dto.BankAccountDto;

import java.net.URI;
import java.util.List;

@AllArgsConstructor
@RestController
public class BankAccountController {
    private final BankAccountFacade bankAccountFacade;
    private final BankAccountQueryFacade bankAccountQueryFacade;
    private final BankAccountWithLinksAssembler assembler;

    @GetMapping("/bank-account/{index}")
    public ResponseEntity<BankAccountWithLinks> getAccountByIndex(@PathVariable Integer index) {
        return ResponseEntity.of(bankAccountQueryFacade
                .findByIndex(index)
                .map(assembler::toModel));
    }

    @GetMapping("/bank-accounts")
    public ResponseEntity<CollectionModel<BankAccountWithLinks>> getAccounts() {
        final List<BankAccountDto> accounts = bankAccountQueryFacade.getAccounts();
        final CollectionModel<BankAccountWithLinks> bankAccountsWithLinks = assembler.toModel(accounts);
        return ResponseEntity.ok(bankAccountsWithLinks);
    }

    @PostMapping("/bank-accounts")
    public ResponseEntity<BankAccountWithLinks> createBankAccount(@RequestBody BankAccountDto bankAccountDto) {
        BankAccountDto createdBankAccount = bankAccountFacade.addBankAccount(bankAccountDto);
        final BankAccountWithLinks bankAccountWithLinks = assembler.toModel(createdBankAccount);
        final URI selfUri = bankAccountWithLinks.getSelfUri();
        return ResponseEntity.created(selfUri).body(bankAccountWithLinks);
    }

    @PutMapping("/bank-account/{index}")
    public ResponseEntity<BankAccountWithLinks> updateBankAccount(@PathVariable Integer index, @RequestBody BankAccountDto bankAccountDto) {
        final BankAccountDto updatedBankAccount = bankAccountFacade.updateBankAccount(index, bankAccountDto);
        final BankAccountWithLinks updatedBankAccountWithLinks = assembler.toModel(updatedBankAccount);
        return ResponseEntity.ok(updatedBankAccountWithLinks);
    }

    @DeleteMapping("/bank-account/{index}")
    public ResponseEntity<Void> deleteBankAccount(@PathVariable Integer index) {
        bankAccountFacade.deleteBankAccount(index);
        return ResponseEntity.noContent().build();
    }
}
