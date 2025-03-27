package tricode.eduve.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tricode.eduve.domain.Conversation;
import tricode.eduve.domain.User;

import java.util.List;
import java.util.Optional;

public interface ConversationRepository extends JpaRepository<Conversation, Long> {
    List<Conversation> findAllByUser(User user);

    Optional<Conversation> findTopByUserOrderByCreatedTimeDesc(User user);
}
