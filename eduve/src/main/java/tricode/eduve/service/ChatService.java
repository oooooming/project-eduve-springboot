package tricode.eduve.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import tricode.eduve.domain.Message;
import tricode.eduve.dto.request.MessageRequestDto;
import tricode.eduve.dto.MessageUnitDto;
import tricode.eduve.global.ChatGptClient;
import tricode.eduve.global.FlaskComponent;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Transactional
public class ChatService {

    private final MessageService messageService;
    private final ChatGptClient chatGptClient;
    private final FlaskComponent flaskComponent;

    // 질문을 저장하고 비동기적으로 ChatGPT API 호출
    @Async
    public Long processQuestionAsync(MessageRequestDto requestDto) {
        // 질문 저장
        Message message = messageService.save(requestDto);

        // ChatGPT API 호출을 비동기로 실행
        CompletableFuture.runAsync(() -> {
            // 유사도 검색
            String similarDocuments = flaskComponent.findSimilarDocuments(requestDto.getQuestion());
            // 유사도 검색결과와 사용자 질문을 함께 chatGPT로 보냄
            String response = chatGptClient.getChatGptResponse(message.getQuestion(), similarDocuments);
            message.setAnswer(response);
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
    public Message processQuestion(MessageRequestDto requestDto, String similarDocuments) {
        Message message = messageService.save(requestDto);

        String response = chatGptClient.getChatGptResponse(message.getQuestion(), similarDocuments);
        message.setAnswer(response);
        message.setStatus(Message.Status.COMPLETED);

        return message;
    }
}
