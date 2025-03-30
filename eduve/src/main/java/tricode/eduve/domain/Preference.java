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
public class Preference {

    @Id
    @Column(name = "preference_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long preferenceId;

    @Enumerated(EnumType.STRING) // Enum을 문자열로 저장
    @Column(nullable = false)
    private Tone tone;

    @Enumerated(EnumType.STRING) // Enum을 문자열로 저장
    @Column(nullable = false)
    private DescriptionLevel descriptionLevel;


    // 기본값을 설정하는 메서드
    public static Preference createDefaultPreference() {
        Preference preference = new Preference();
        preference.setTone(Tone.FRIENDLY);
        preference.setDescriptionLevel(DescriptionLevel.MEDIUM);
        return preference;
    }
}
