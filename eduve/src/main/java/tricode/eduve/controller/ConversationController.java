package tricode.eduve.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tricode.eduve.dto.MessageRequestDto;
import tricode.eduve.dto.MessageResponseDto;
import tricode.eduve.service.ChatService;
import tricode.eduve.service.ConversationService;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/conversations")
public class ConversationController {

    private final ConversationService conversationService;
    private final ChatService chatService;

    // 동기식 처리
    // 새로운 대화 시작
    @PostMapping("/start")
    public ResponseEntity<MessageResponseDto> startConversation(@RequestBody MessageRequestDto requestDto) {
        return ResponseEntity.ok(conversationService.startConversation(requestDto));
    }



    // 비동기 처리
    // 1. 질문을 받고 비동기 처리 시작
    @PostMapping("/start/...비동기어쩌구")
    public ResponseEntity<Map<String, Object>> askQuestion(@RequestBody MessageRequestDto requestDto) {
        return ResponseEntity.ok(conversationService.startConversationAsync(requestDto));
    }

    // 2. 질문 응답 조회 (프론트에서 폴링)
    @GetMapping("/{messageId}")
    public ResponseEntity<MessageResponseDto> getAnswer(@PathVariable Long messageId) {
        return ResponseEntity.ok(chatService.getAnswer(messageId));
    }


//    // 특정 사용자의 대화 목록 조회
//    @GetMapping("/{userId}")
//    public ResponseEntity<List<Conversation>> getUserConversations(@PathVariable Long userId) {
//        return ResponseEntity.ok(conversationService.getUserConversations(userId));
//    }
//
//    // 특정 대화의 메시지 목록 조회
//    @GetMapping("/{conversationId}/messages")
//    public ResponseEntity<List<Message>> getMessagesByConversationId(@PathVariable Long conversationId) {
//        return ResponseEntity.ok(conversationService.getMessagesByConversationId(conversationId));
//    }
//
//    // 대화에 메시지 추가
//    @PostMapping("/{conversationId}/messages")
//    public ResponseEntity<Message> addMessage(@PathVariable Long conversationId, @RequestParam String content) {
//        return ResponseEntity.ok(conversationService.addMessage(conversationId, content));
//    }
}
