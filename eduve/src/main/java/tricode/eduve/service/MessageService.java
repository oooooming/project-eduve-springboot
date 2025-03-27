package tricode.eduve.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tricode.eduve.domain.Conversation;
import tricode.eduve.domain.Message;
import tricode.eduve.dto.request.MessageRequestDto;
import tricode.eduve.repository.MessageRepository;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;

    /*
    // 메시지 저장
    public Message save(MessageRequestDto requestDto, Conversation conversation) {
        Message message = new Message(requestDto.getUserId(), requestDto.getQuestion(), conversation);
        return messageRepository.save(message);
    }

    public Message findById(Long messageId) {
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new RuntimeException("Message not found"));
        return message;
    }
     */
}
