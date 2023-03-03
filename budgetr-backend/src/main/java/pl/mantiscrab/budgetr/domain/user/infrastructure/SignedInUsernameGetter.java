package pl.mantiscrab.budgetr.domain.user.infrastructure;

public interface SignedInUsernameGetter {
    String getUsername() throws UserNotAuthenticatedException;
}
