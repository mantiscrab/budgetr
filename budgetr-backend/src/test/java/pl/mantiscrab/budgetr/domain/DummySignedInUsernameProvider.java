package pl.mantiscrab.budgetr.domain;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import pl.mantiscrab.budgetr.domain.infrastructure.SignedInUsernameProvider;
import pl.mantiscrab.budgetr.domain.infrastructure.UserNotAuthenticatedException;

@AllArgsConstructor
@NoArgsConstructor
public class DummySignedInUsernameProvider implements SignedInUsernameProvider {

    private String signedInUserName;

    public void setSignedInUserName(String name) {
        this.signedInUserName = name;
    }

    @Override
    public String getUsername() throws UserNotAuthenticatedException {
        return signedInUserName;
    }
}
