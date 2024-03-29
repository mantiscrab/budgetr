package pl.mantiscrab.budgetr.auth;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
@Entity
@Table(name = "users")
class UserAuth {
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
        if (authorities == null) {
            authorities = new HashSet<>();
        }
        authorities.add(authority);
    }
}
