package tricode.eduve.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import tricode.eduve.domain.Message;
import tricode.eduve.dto.request.MessageRequestDto;
import tricode.eduve.dto.MessageUnitDto;
import tricode.eduve.global.ChatGptClient;
import tricode.eduve.global.FlaskComponent;


@Service
@RequiredArgsConstructor
@Transactional
public class ChatService {

    private final ChatGptClient chatGptClient;
    private final FlaskComponent flaskComponent;
    private final ConversationService conversationService;

    /*
    // 질문을 저장하고 비동기적으로 ChatGPT API 호출
    @Async
    public Long processQuestionAsync(MessageRequestDto requestDto) {
        // 임시 conversation 수정필요
        Conversation conversation = new Conversation();


        // 질문 저장
        Message message = messageService.save(requestDto, conversation);

        // ChatGPT API 호출을 비동기로 실행
        CompletableFuture.runAsync(() -> {
            // 유사도 검색
            String similarDocuments = flaskComponent.findSimilarDocuments(requestDto.getQuestion());
            // 유사도 검색결과와 사용자 질문을 함께 chatGPT로 보냄
            ResponseEntity<String> response= chatGptClient.chat(message.getQuestion(), similarDocuments);
            String parsedResponse = null;
            try {
                parsedResponse = parseResponse(response);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }

            message.setAnswer(parsedResponse);
            message.setStatus(Message.Status.COMPLETED);
        });

        return message.getMessageId();
    }

    // 메시지 응답 조회(비동기)
    public MessageUnitDto getAnswer(Long messageId) {
        Message message = messageService.findById(messageId);
        return MessageUnitDto.from(message);
    }


    // 메시지 동기 처리
    public Message processQuestion(MessageRequestDto requestDto, String similarDocuments, Conversation conversation) throws JsonProcessingException {
        Message message = messageService.save(requestDto, conversation);

        ResponseEntity<String> response= chatGptClient.chat(message.getQuestion(), similarDocuments);
        String parsedResponse = parseResponse(response);

        message.setAnswer(parsedResponse);
        message.setStatus(Message.Status.COMPLETED);

        return message;
    }

     */

    public String parseResponse(ResponseEntity<String> response) throws JsonProcessingException {
        String responseBody = response.getBody();  // 응답 본문 받기
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode = objectMapper.readTree(responseBody);

        // 'choices' 배열의 첫 번째 항목에서 'message' -> 'content' 추출
        String answer = rootNode.path("choices").get(0).path("message").path("content").asText();
        return answer;
    }


    public MessageUnitDto startConversation(MessageRequestDto requestDto, Long userId) throws JsonProcessingException{

        String userMessage = requestDto.getQuestion();

        // 1. Conversation 처리 (주제 유사도검색 + 1시간 기준)
        Message message = conversationService.processUserMessage(userId, userMessage);

        // 질문 유사도 검색
        String similarDocuments = flaskComponent.findSimilarDocuments(userMessage);

        // 2. ChatGPT API 호출
        ResponseEntity<String> response= chatGptClient.chat(userMessage, similarDocuments);
        String parsedResponse = parseResponse(response);

        // 3. 봇 응답 저장
        Message botMessage = Message.createBotResponse(message.getConversation(), parsedResponse, message);
        conversationService.saveBotMessage(botMessage);

        return MessageUnitDto.from(message,botMessage);
    }
}
