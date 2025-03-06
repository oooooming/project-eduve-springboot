package tricode.eduve.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tricode.eduve.domain.Message;
import tricode.eduve.dto.MessageRequestDto;
import tricode.eduve.repository.MessageRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MessageService {
    
    private final MessageRepository messageRepository;
    
    // 메시지 저장
    public Message save(MessageRequestDto requestDto) {
        Message message = new Message(requestDto.getUserId(), requestDto.getQuestion());
        return messageRepository.save(message);
    }

    public Message findById(Long messageId) {
        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new RuntimeException("Message not found"));
        return message;
    }
}
