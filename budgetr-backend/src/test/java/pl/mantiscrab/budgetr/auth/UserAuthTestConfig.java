package pl.mantiscrab.budgetr.auth;

import org.springframework.security.crypto.password.NoOpPasswordEncoder;

class UserAuthTestConfig {
    static UserAuthService userService() {
        return new UserAuthConfig().userAuthService(NoOpPasswordEncoder.getInstance(), new InMemoryUserAuthRepository());
    }
}
