package pl.mantiscrab.budgetr.auth;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@ToString
@Entity
@Table(name = "authorities")
@IdClass(AuthorityId.class)
class Authority implements Serializable {
    @Id
    @JoinColumn(name = "username")
    @ManyToOne
    private UserAuth user;
    @Id
    private String authority;
}

@EqualsAndHashCode
class AuthorityId implements Serializable {
    private String authority;
    private UserAuth user;
}

