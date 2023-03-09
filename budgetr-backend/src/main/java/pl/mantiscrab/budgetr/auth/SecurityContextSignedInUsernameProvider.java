package pl.mantiscrab.budgetr.auth;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import pl.mantiscrab.budgetr.domain.user.exceptions.BudgetrUserNotAuthenticatedException;
import pl.mantiscrab.budgetr.domain.user.infrastructure.SignedInUsernameProvider;

class SecurityContextSignedInUsernameProvider implements SignedInUsernameProvider {
    @Override
    public String getUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(userIsNotAuthenticated(authentication)){
            throw new BudgetrUserNotAuthenticatedException();
        }
        return authentication.getName();
    }

    private static boolean userIsNotAuthenticated(Authentication authentication) {
        return !authentication.isAuthenticated();
    }
}
