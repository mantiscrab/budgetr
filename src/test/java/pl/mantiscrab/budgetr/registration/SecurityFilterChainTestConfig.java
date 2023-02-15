package pl.mantiscrab.budgetr.registration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import pl.mantiscrab.budgetr.config.SecurityFilterChainConfig;

@Configuration
class SecurityFilterChainTestConfig {

    @Bean
    @Profile("test")
    public SecurityFilterChain filterChainTest(HttpSecurity http) throws Exception {
        http = new SecurityFilterChainConfig().httpSecurityCommons(http);
        http.authorizeHttpRequests(requests -> requests
                .anyRequest().authenticated());
        http.httpBasic();
        http.csrf().disable();
        return http.build();
    }
}
