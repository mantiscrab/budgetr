package pl.mantiscrab.budgetr.domain.transaction;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class TransactionConfig {
    @Bean
    TransactionService transactionService() {
        return new TransactionService();
    }
}
