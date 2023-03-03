package pl.mantiscrab.budgetr.domain.user;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class DummySignedInUserGetter implements SignedInUserGetter {

    private User signedInUser;

    @Override
    public User getUser() {
        return signedInUser;
    }

    public void setSignedInUser(User signedInUser) {
        this.signedInUser = signedInUser;
    }
}
