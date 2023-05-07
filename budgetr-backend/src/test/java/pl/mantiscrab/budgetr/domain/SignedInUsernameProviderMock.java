package pl.mantiscrab.budgetr.domain;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import pl.mantiscrab.budgetr.domain.infrastructure.SignedInUsernameProvider;

@AllArgsConstructor
@NoArgsConstructor
class SignedInUsernameProviderMock implements SignedInUsernameProvider {

    private String signedInUserName;

    void setSignedInUserName(final String name) {
        this.signedInUserName = name;
    }

    @Override
    public String getUsername() {
        return signedInUserName;
    }
}
