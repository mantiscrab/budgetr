package pl.mantiscrab.budgetr.domain.infrastructure;

public interface SignedInUsernameProvider {
    String getUsername() throws UserNotAuthenticatedException;
}
