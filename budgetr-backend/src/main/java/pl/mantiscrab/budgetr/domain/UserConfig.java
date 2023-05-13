package pl.mantiscrab.budgetr.domain;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.mantiscrab.budgetr.domain.infrastructure.RecentlyAuthenticatedUsersPublisher;
import pl.mantiscrab.budgetr.domain.infrastructure.SignedInUsernameProvider;

@AllArgsConstructor
@Configuration
class UserConfig {

    private final UserRepository userRepository;
    private final SignedInUsernameProvider userNameProvider;
    private final RecentlyAuthenticatedUsersPublisher publisher;

    @Bean
    BankAccountQueryFacade BankAccountQueryFacade() {
        return new BankAccountQueryFacade(
                signedInUserProvider(),
                this.userRepository);
    }

    @Bean
    BankAccountService bankAccountService() {
        return new BankAccountService(
                signedInUserProvider(),
                this.userRepository);
    }

    @Bean
    BankAccountFacade bankAccountFacade() {
        return new BankAccountFacade(this.bankAccountService());
    }

    @Bean
    UserService userService() {
        UserService userService = new UserService(userRepository);
        publisher.subscribe(userService);
        return userService;
    }

    @Bean
    SignedInUserProvider signedInUserProvider() {
        return new SignedInUserProviderImpl(userNameProvider, userRepository);
    }
}
