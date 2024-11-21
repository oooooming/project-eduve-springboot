package tricode.eduve.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Preference {

    @Id
    @Column(name = "preference_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int preferenceId;

    @Column(nullable = false)
    private String tone;

    @Column(nullable = false)
    private String descriptionLevel;

}
