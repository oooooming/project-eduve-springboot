package tricode.eduve.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tricode.eduve.domain.Message;
import tricode.eduve.domain.MessageLike;

import java.util.Optional;

@Repository
public interface MessageLikeRepository extends JpaRepository<MessageLike, Long> {
    boolean existsByMessage(Message message);

    Optional<MessageLike> findByMessage(Message message);
}
