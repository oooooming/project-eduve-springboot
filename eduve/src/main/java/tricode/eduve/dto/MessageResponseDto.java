package tricode.eduve.dto;


import lombok.RequiredArgsConstructor;
import tricode.eduve.domain.Message;
import java.time.LocalDateTime;

@RequiredArgsConstructor
public class MessageResponseDto {
    private final Long conversationId;
    private final Long messageId;
    private final String question;
    private final String answer;
    private final String status;
    private final LocalDateTime createdTime;


    public static MessageResponseDto from(Message message) {
        return new MessageResponseDto(
                message.getConversation().getConversationId(),
                message.getMessageId(),
                message.getQuestion(),
                message.getAnswer(),
                message.getStatus().toString(),
                message.getCreatedTime()
        );
    }
}
