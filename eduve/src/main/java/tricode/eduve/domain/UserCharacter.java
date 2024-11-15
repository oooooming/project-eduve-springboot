package tricode.eduve.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserCharacter {

    @Id
    @Column(name = "userCharacter_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userCharacterId;

    @Column
    private String userCharacterName;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    private Character chatacter;

    @OneToOne
    private Preference preference;

}
