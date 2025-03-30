package tricode.eduve.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import tricode.eduve.domain.Conversation;

import java.util.List;

@Getter
@AllArgsConstructor
public class ConversationWithMessagesDto {

    private Long conversationId;
    private String conversationName;
    private List<MessageDto> messages;

    @Getter
    @AllArgsConstructor
    public static class MessageDto {
        private String content;
        private boolean isUserMessage;
    }

    // Message 목록을 전달하기 위한 메서드
    public static ConversationWithMessagesDto fromConversation(Conversation conversation) {
        List<MessageDto> messageDtos = conversation.getMessages().stream()
                .map(message -> new MessageDto(message.getContent(), message.isUserMessage()))
                .toList();

        return new ConversationWithMessagesDto(conversation.getConversationId(), conversation.getConversationName(), messageDtos);
    }
}