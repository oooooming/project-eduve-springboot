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
    @Column(name = "user_character_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userCharacterId;

    @Column
    private String userCharacterName;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "character_id", nullable = false)
    private AllCharacter character;

    @OneToOne
    @JoinColumn(name = "preference_id", nullable = false)
    private Preference preference;


    // 기본값을 설정하는 메서드
    public static UserCharacter createDefaultUserCharacter(User user, AllCharacter character) {
        UserCharacter userCharacter = new UserCharacter();
        userCharacter.setUser(user);
        // characterId 1인 캐릭터 가져와서 파라미터 넣어주면 될 듯
        userCharacter.setCharacter(character);
        userCharacter.setUserCharacterName(character.getCharacterName());
        // 기본 Preference 설정
        userCharacter.setPreference(Preference.createDefaultPreference());
        return userCharacter;
    }
}
