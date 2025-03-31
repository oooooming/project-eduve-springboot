package tricode.eduve.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import tricode.eduve.domain.Conversation;
import tricode.eduve.dto.MessageUnitDto;
import tricode.eduve.dto.OneMessageDto;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class MessagesResponseDto {
    private final Long conversationId;
    private final String conversationName;
    private final List<OneMessageDto> messageList;

    public static MessagesResponseDto of(Conversation conversation, List<OneMessageDto> messages) {
        return new MessagesResponseDto(
                conversation.getConversationId(),
                conversation.getConversationName(),
                messages
        );
    }
}
