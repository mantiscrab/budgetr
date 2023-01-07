package pl.mantiscrab.budgetr.registration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class RegistrationConfig {
    @Bean
    RegistrationFacade registrationFacade(UserRepository userRepository) {
        return new RegistrationFacade(userRepository);
    }
}
