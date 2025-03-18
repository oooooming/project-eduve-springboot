package tricode.eduve.dto;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import tricode.eduve.domain.Message;
import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class MessageUnitDto {
    private final Long messageId;
    private final String question;
    private final String answer;
    private final String status;
    private final LocalDateTime createdTime;


    public static MessageUnitDto from(Message message) {
        return new MessageUnitDto(
                message.getMessageId(),
                message.getQuestion(),
                message.getAnswer(),
                message.getStatus().toString(),
                message.getCreatedTime()
        );
    }
}
