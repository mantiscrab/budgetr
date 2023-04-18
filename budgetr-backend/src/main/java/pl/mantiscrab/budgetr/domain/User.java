package pl.mantiscrab.budgetr.domain;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@AllArgsConstructor(access = AccessLevel.PACKAGE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter(AccessLevel.PACKAGE)
@EqualsAndHashCode
@Entity
@Table(name = "BUDGETR_USERS")
class User {
    @Id
    @Column(nullable = false)
    @NotBlank
    private String username;

    @Column(nullable = false, unique = true)
    @Email
    @NotBlank
    private String email;

    public void updateEmail(String email) {
        this.email = email;
    }
}
