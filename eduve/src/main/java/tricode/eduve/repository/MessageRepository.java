package tricode.eduve.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tricode.eduve.domain.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
