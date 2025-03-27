package tricode.eduve.dto;


import lombok.Getter;
import lombok.RequiredArgsConstructor;
import tricode.eduve.domain.Message;

@Getter
@RequiredArgsConstructor
public class MessageUnitDto {
    private final UserMessage userMessage;
    private final BotMessage botMessage;


    public static MessageUnitDto from(Message userMessage, Message botMessage) {
        return new MessageUnitDto(
                new UserMessage(userMessage),
                new BotMessage(botMessage)
        );
    }
}

