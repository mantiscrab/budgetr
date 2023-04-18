package pl.mantiscrab.budgetr.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class BankAccountConfig {
    @Bean
    BankAccountService bankAccountService(BankAccountRepository repository, SignedInUserProvider userGetter) {
        return new BankAccountService(repository, userGetter);
    }
}
