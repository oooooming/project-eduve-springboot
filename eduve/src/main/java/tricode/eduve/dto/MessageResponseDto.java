package tricode.eduve.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import tricode.eduve.domain.Message;

@Getter
@AllArgsConstructor
public class MessageResponseDto {

    private String content; // 메시지 내용
    private boolean isUserMessage; // 사용자 메시지 여부

    // Message 객체를 DTO로 변환하는 메서드
    public static MessageResponseDto fromMessage(Message message) {
        return new MessageResponseDto(message.getContent(), message.isUserMessage());
    }
}
