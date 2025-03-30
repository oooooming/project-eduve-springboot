package tricode.eduve.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class MessageRequestDto {
    @NotNull private String question;
}
