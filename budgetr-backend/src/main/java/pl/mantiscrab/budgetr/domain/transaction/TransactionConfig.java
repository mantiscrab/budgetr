package pl.mantiscrab.budgetr.domain.transaction;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.mantiscrab.budgetr.domain.bankaccount.BankAccountService;

@Configuration
class TransactionConfig {
    @Bean
    TransactionService transactionService(BankAccountService bankAccountService, TransactionRepository transactionRepository) {
        return new TransactionService(bankAccountService, transactionRepository);
    }
}
