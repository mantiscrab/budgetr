package pl.mantiscrab.budgetr.auth;

import org.springframework.security.crypto.password.NoOpPasswordEncoder;

class UserTestConfig {
    static UserAuthService userService() {
        return new UserAuthConfig().userAuthService(NoOpPasswordEncoder.getInstance(), new InMemoryUserAuthRepository());
    }
}
