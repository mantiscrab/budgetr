package pl.mantiscrab.budgetr.domain.user.infrastructure;

public interface SignedInUsernameProvider {
    String getUsername() throws UserNotAuthenticatedException;
}
