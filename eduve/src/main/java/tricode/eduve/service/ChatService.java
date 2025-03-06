package tricode.eduve.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import tricode.eduve.domain.Message;
import tricode.eduve.dto.MessageRequestDto;
import tricode.eduve.dto.MessageResponseDto;
import tricode.eduve.global.ChatGptClient;

import java.util.concurrent.CompletableFuture;

@Service
@RequiredArgsConstructor
@Transactional
public class ChatService {

    private final MessageService messageService;
    private final ChatGptClient chatGptClient;

    // 질문을 저장하고 비동기적으로 ChatGPT API 호출
    @Async
    public Long processQuestionAsync(MessageRequestDto requestDto) {
        // 질문 저장
        Message message = messageService.save(requestDto);

        // ChatGPT API 호출을 비동기로 실행
        CompletableFuture.runAsync(() -> {
            String response = chatGptClient.getChatGptResponse(message.getQuestion());
            message.setAnswer(response);
            message.setStatus(Message.Status.COMPLETED);
        });

        return message.getMessageId();
    }

    // 메시지 응답 조회(비동기)
    public MessageResponseDto getAnswer(Long messageId) {
        Message message = messageService.findById(messageId);
        return MessageResponseDto.from(message);
    }


    public Message processQuestion(MessageRequestDto requestDto) {
        Message message = messageService.save(requestDto);

        String response = chatGptClient.getChatGptResponse(message.getQuestion());
        message.setAnswer(response);
        message.setStatus(Message.Status.COMPLETED);

        return message;
    }
}
