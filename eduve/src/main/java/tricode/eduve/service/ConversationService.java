package tricode.eduve.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tricode.eduve.domain.Conversation;
import tricode.eduve.domain.Message;
import tricode.eduve.domain.User;
import tricode.eduve.dto.MessageRequestDto;
import tricode.eduve.dto.MessageResponseDto;
import tricode.eduve.global.FlaskComponent;
import tricode.eduve.repository.ConversationRepository;

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

    public MessageResponseDto startConversation(MessageRequestDto requestDto) {
        
        // 유사도 검색 로직
        flaskComponent.findSimilarDocuments(requestDto.getQuestion());
        
        Message message = chatService.processQuestion(requestDto);
        return MessageResponseDto.from(message);
    }

    // 비동기식
    public Map<String, Object> startConversationAsync(MessageRequestDto requestDto) {
        
        // 유사도 검색 로직
        // 이거를 processQuestionAsync안에 넣어서 같이 비동기로 해야될 것 같은데
        
        Long messageId = chatService.processQuestionAsync(requestDto);

        Map<String, Object> response = new HashMap<>();
        response.put("messageId", messageId);
        response.put("status", "PROCESSING");

        return response;
    }


    public List<Conversation> getUserConversations(Long userId) {
        User user = userService.findById(userId);
        return conversationRepository.findAllByUser(user);
    }

    public List<Message> getMessagesByConversationId(Long conversationId) {
        Conversation conversation = conversationRepository.findById(conversationId)
                .orElseThrow();
        return conversation.getMessages();
    }
}
