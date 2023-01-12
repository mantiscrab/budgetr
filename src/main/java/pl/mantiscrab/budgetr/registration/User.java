package pl.mantiscrab.budgetr.registration;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
@Entity
@Table(name = "users")
class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, unique = true)
    @Email
    @NotBlank
    private String email;
    @Column(nullable = false, unique = true)
    @NotBlank
    private String username;
    @Column(nullable = false)
    @NotBlank
    private String password;

}
