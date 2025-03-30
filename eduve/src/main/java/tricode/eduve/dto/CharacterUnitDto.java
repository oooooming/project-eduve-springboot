package tricode.eduve.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tricode.eduve.domain.AllCharacter;
import tricode.eduve.domain.UserCharacter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CharacterUnitDto {
    private Long characterId;
    private String characterName;
    //private String characterImgUrl;

    public static CharacterUnitDto from(AllCharacter character) {
        return new CharacterUnitDto(
                character.getAllCharacterId(),
                character.getCharacterName()
        );
    }
}
