package tricode.eduve.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OneMessageDto {
    private final Long messageId;
    private final String content;
    private final boolean isUserMessage;
    private final Long questionMessageId;
    private final LocalDateTime createdTime;
}
