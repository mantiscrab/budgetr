package pl.mantiscrab.budgetr.domain.bankaccount;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.mantiscrab.budgetr.domain.user.SignedInUserProvider;

@Configuration
class BankAccountConfig {
    @Bean
    BankAccountService bankAccountService(BankAccountRepository repository, SignedInUserProvider userGetter) {
        return new BankAccountService(repository, userGetter);
    }
}
