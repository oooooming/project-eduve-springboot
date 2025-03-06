package tricode.eduve.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tricode.eduve.domain.Conversation;

public interface ConversationRepository extends JpaRepository<Conversation, Long> {
}
