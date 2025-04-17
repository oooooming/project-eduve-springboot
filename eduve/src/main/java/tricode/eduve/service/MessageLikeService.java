package tricode.eduve.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tricode.eduve.domain.Message;
import tricode.eduve.domain.MessageLike;
import tricode.eduve.repository.MessageLikeRepository;
import tricode.eduve.repository.MessageRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class MessageLikeService {

    private final MessageLikeRepository messageLikeRepository;
    private final MessageRepository messageRepository;

    // 메시지 좋아요 생성
    public String createMessageLike(Long messageId) {
        Message message = messageRepository.findById(messageId)
                .orElseThrow(()-> new RuntimeException("Message not found."));

        if(message.getQuestionMessage()==null){
            throw new RuntimeException("Message is user message");
        }

        boolean isLiked = messageLikeRepository.existsByMessage(message);

        // 메시지 좋아요가 이미 있으면 생성하지 않음
        if(isLiked) {
            throw new RuntimeException("Message like already exists.");
        }
        else{
            MessageLike messageLike = new MessageLike(message);
            messageLikeRepository.save(messageLike);

            return "Message Like created successfully";
        }
    }


    // 메시지 좋아요 삭제
    public String deleteMessageLike(Long messageId) {
        Message message = messageRepository.findById(messageId)
                .orElseThrow(()-> new RuntimeException("Message not found."));

        if(message.getQuestionMessage()==null){
            throw new RuntimeException("Message is user message");
        }

        MessageLike messageLike = messageLikeRepository.findByMessage(message)
                .orElseThrow(()-> new RuntimeException("MessageLike not found."));

        messageLikeRepository.delete(messageLike);
        return "Message Like deleted";
    }
}
