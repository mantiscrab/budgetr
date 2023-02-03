package pl.mantiscrab.budgetr.registration;

import lombok.EqualsAndHashCode;

import java.io.Serializable;

@EqualsAndHashCode
class AuthorityId implements Serializable {
    private String authority;
    private User user;
}
