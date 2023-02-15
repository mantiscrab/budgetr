package pl.mantiscrab.budgetr.config;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityFilterChainConfig {

    public HttpSecurity httpSecurityCommons(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(requests -> requests
                .mvcMatchers("/register").permitAll());
        http.formLogin();
        return http;
    }

    @Bean
    @Profile("dev")
    public SecurityFilterChain filterChainDev(HttpSecurity http) throws Exception {
        http = httpSecurityCommons(http);
        http.authorizeHttpRequests(requests -> requests
                .mvcMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                .requestMatchers(PathRequest.toH2Console()).permitAll()
                .anyRequest().authenticated());
        http.httpBasic();
        http.csrf().disable();
        http.headers().frameOptions().sameOrigin();
        return http.build();
    }
}
