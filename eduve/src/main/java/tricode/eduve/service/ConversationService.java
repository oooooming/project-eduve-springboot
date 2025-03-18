package tricode.eduve.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tricode.eduve.domain.Conversation;
import tricode.eduve.domain.Message;
import tricode.eduve.domain.User;
import tricode.eduve.dto.response.ConversationResponseDto;
import tricode.eduve.dto.request.MessageRequestDto;
import tricode.eduve.dto.MessageUnitDto;
import tricode.eduve.dto.response.MessagesResponseDto;
import tricode.eduve.global.FlaskComponent;
import tricode.eduve.repository.ConversationRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class ConversationService {

    private final ChatService chatService;
    private final FlaskComponent flaskComponent;
    private final UserService userService;
    private final ConversationRepository conversationRepository;

    public MessageUnitDto startConversation(MessageRequestDto requestDto) {
        
        // 유사도 검색 로직
        String similarDocuments = flaskComponent.findSimilarDocuments(requestDto.getQuestion());
        
        Message message = chatService.processQuestion(requestDto, similarDocuments);
        return MessageUnitDto.from(message);
    }

    // 비동기식
    public Map<String, Object> startConversationAsync(MessageRequestDto requestDto) {
        
        Long messageId = chatService.processQuestionAsync(requestDto);

        Map<String, Object> response = new HashMap<>();
        response.put("messageId", messageId);
        response.put("status", "PROCESSING");

        return response;
    }


    public List<ConversationResponseDto> getUserConversations(Long userId) {
        User user = userService.findById(userId);
        List<Conversation> conversationList = conversationRepository.findAllByUser(user);
        return ConversationResponseDto.from(conversationList);
    }


    public MessagesResponseDto getMessagesByConversationId(Long conversationId) {
        Conversation conversation = conversationRepository.findById(conversationId)
                .orElseThrow();
        List<Message> messages = conversation.getMessages();
        List<MessageUnitDto> messageUnitDtos = new ArrayList<>();
        for(Message message : messages) {
            messageUnitDtos.add(
                    MessageUnitDto.from(message)
            );
        }
        return MessagesResponseDto.of(conversation, messageUnitDtos);
    }
}
