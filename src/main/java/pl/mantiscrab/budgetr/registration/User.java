package pl.mantiscrab.budgetr.registration;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
@Entity
@Table(name = "users")
class User {
    @Id
    @Column(nullable = false)
    @NotBlank
    private String username;

    @Column(nullable = false, unique = true)
    @Email
    @NotBlank
    private String email;

    @Column(nullable = false)
    @NotBlank
    private String password;

    @Column(nullable = false)
    private boolean enabled;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    @ToString.Exclude
    private Set<Authority> authorities;

    void addAuthority(Authority authority) {
        authorities.add(authority);
    }
}
