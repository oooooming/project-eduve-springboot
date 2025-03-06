package tricode.eduve.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tricode.eduve.global.CreatedTimeEntity;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Message extends CreatedTimeEntity {

    @Id
    @Column(name = "message_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long MessageId;

    // 대화 세션(대화 단위? -> 어떤 기준으로 나눌건지)
    @ManyToOne
    @JoinColumn(name = "conversation_id", nullable = false)
    private Conversation conversation;

    // 사용자 질문 내용
    @Column(columnDefinition = "TEXT", nullable = false)
    private String question;

    // AI 답변
    @Column(columnDefinition = "TEXT")
    private String answer;
    
    // 메시지 처리 상태
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status;
    
    
    
    public enum Status{
        PROCESSING, COMPLETED
    }

    
    
    public Message(Long userId, String question) {
        //this.conversation = ...
        this.MessageId = userId;
        this.question = question;
    }
    public Message(String answer) {
        this.answer = answer;
    }
}
