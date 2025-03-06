package tricode.eduve.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class MessageRequestDto {

    @NotNull private String question;
    @NotNull private Long userId;

}
