package ai.pribela.glossapp.repository.data;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Learner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, name = "auth0_id")
    private String auth0Id;

    @Column(unique = true)
    private String username;

    public Learner(String username, String auth0Id) {
        this.username = username;
        this.auth0Id = auth0Id;
    }

}
