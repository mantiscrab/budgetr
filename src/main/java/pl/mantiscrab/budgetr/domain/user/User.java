package pl.mantiscrab.budgetr.domain.user;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity
@Table(name = "budgetr_users")
class User {
    @Id
    @Column(nullable = false)
    @NotBlank
    private String username;

    @Column(nullable = false, unique = true)
    @Email
    @NotBlank
    private String email;
}
