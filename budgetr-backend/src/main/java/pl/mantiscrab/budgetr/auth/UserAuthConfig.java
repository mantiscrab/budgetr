package pl.mantiscrab.budgetr.auth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
class UserAuthConfig {
    @Bean
    UserAuthService userAuthService(PasswordEncoder passwordEncoder, UserAuthRepository userAuthRepository) {
        return new UserAuthService(passwordEncoder, userAuthRepository);
    }

    @Bean
    SecurityContextSignedInUsernameGetter securityContextSignedInUsernameGetter() {
        return new SecurityContextSignedInUsernameGetter();
    }
}
