package tricode.eduve.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class AllCharacterResponseDto {
    List<CharacterUnitDto> AllCharacterList;
}
