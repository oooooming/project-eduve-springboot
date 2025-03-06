package tricode.eduve.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import tricode.eduve.domain.Message;
import tricode.eduve.dto.MessageRequestDto;
import tricode.eduve.dto.MessageResponseDto;
import tricode.eduve.repository.ConversationRepository;
import tricode.eduve.repository.MessageRepository;
import tricode.eduve.repository.UserRepository;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class ConversationService {

    private final UserRepository userRepository;
    private final ConversationRepository conversationRepository;
    private final MessageRepository messageRepository;
    private final ChatService chatService;

    public MessageResponseDto startConversation(MessageRequestDto requestDto) {
        
        // 유사도 검색 로직
        
        Message message = chatService.processQuestion(requestDto);
        return MessageResponseDto.from(message);
    }

    // 비동기식
    public Map<String, Object> startConversationAsync(MessageRequestDto requestDto) {
        
        // 유사도 검색 로직
        
        Long messageId = chatService.processQuestionAsync(requestDto);

        Map<String, Object> response = new HashMap<>();
        response.put("messageId", messageId);
        response.put("status", "PROCESSING");

        return response;
    }
}
