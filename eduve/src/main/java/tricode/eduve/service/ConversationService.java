package tricode.eduve.service;

import com.fasterxml.jackson.core.JsonProcessingException;
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
import tricode.eduve.repository.MessageRepository;

import java.time.LocalDateTime;
import java.util.*;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class ConversationService {

    private final FlaskComponent flaskComponent;
    private final UserService userService;
    private final ConversationRepository conversationRepository;
    private final MessageRepository messageRepository;

    /*
    public MessageUnitDto startConversation(MessageRequestDto requestDto, Long userId) throws JsonProcessingException {
        User user = userService.findById(userId);

        // 유사도 검색 로직
        String similarDocuments = flaskComponent.findSimilarDocuments(requestDto.getQuestion());

        Conversation conversation = new Conversation("temporary conversationName", user);
        conversationRepository.save(conversation);

        Message message = chatService.processQuestion(requestDto, similarDocuments, conversation);
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

     */







    public Message processUserMessage(Long userId, String userMessage) {
        User user = userService.findById(userId);
        // 1. 최신 Conversation 조회
        Optional<Conversation> lastConversationOpt = conversationRepository.findTopByUserOrderByCreatedTimeDesc(user);
        Conversation conversation;

        if (lastConversationOpt.isPresent()) {
            Conversation lastConversation = lastConversationOpt.get();
            LocalDateTime lastMessageTime = lastConversation.getUpdatedTime();
            String lastTopic = lastConversation.getLastTopic();

            // 2. 시간 초과 여부 (1시간 기준)
            boolean isTimeExceeded = lastMessageTime.isBefore(LocalDateTime.now().minusHours(1));

            // 3. 유사도 검사
            String newTopic = flaskComponent.extractTopic(userMessage);
            double similarity = flaskComponent.calculateSimilarity(lastTopic, newTopic);
            boolean isNewTopic = similarity < 0.8;

            // 4. 새 Conversation 생성 여부
            if (isTimeExceeded || isNewTopic) {
                conversation = createNewConversation(userId, newTopic);
            } else {
                conversation = lastConversation;
            }
        } else {
            // 5. 첫 대화라면 새로운 Conversation 생성
            String topic = flaskComponent.extractTopic(userMessage);
            conversation = createNewConversation(userId, topic);
        }

        // 6. 메시지 저장 및 Conversation 갱신
        Message message = Message.createUserMessage(conversation, userMessage);
        messageRepository.save(message);
        conversation.updateLastTopic(userMessage);
        conversationRepository.save(conversation);

        return message;
    }


    private Conversation createNewConversation(Long userId, String topic) {
        User user = userService.findById(userId);
        Conversation conversation = new Conversation(topic, user);
        return conversationRepository.save(conversation);
    }
}
