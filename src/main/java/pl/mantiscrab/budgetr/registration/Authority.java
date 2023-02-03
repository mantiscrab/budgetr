package pl.mantiscrab.budgetr.registration;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@ToString
@Entity
@Table(name = "authorities")
@IdClass(AuthorityId.class)
class Authority implements Serializable {
    @Id
    @JoinColumn(name = "username")
    @ManyToOne()
    private User user;
    @Id
    private String authority;
}
