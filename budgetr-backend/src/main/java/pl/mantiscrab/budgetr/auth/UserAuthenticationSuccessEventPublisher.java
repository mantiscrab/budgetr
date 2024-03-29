package pl.mantiscrab.budgetr.auth;

import lombok.AllArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Service;
import pl.mantiscrab.budgetr.domain.infrastructure.RecentlyAuthenticatedUsersPublisher;

import java.util.Optional;

@AllArgsConstructor
@Service
class UserAuthenticationSuccessEventPublisher extends RecentlyAuthenticatedUsersPublisher {
    private final UserAuthRepository userAuthRepository;

    @EventListener
    public void onSuccess(AuthenticationSuccessEvent success) {
        String username = success.getAuthentication().getName();
        Optional<UserAuth> optionalUser = userAuthRepository.findByUsername(username);
        if (optionalUser.isPresent()) {
            UserAuth user = optionalUser.get();
            RecentlyAuthenticatedUserImpl authenticatedUser =
                    new RecentlyAuthenticatedUserImpl(user.getUsername(), user.getEmail());
            this.notify(authenticatedUser);
        }
    }
}
