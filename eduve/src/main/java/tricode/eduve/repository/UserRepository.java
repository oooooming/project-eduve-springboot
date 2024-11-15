package tricode.eduve.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tricode.eduve.domain.User;

public interface UserRepository extends JpaRepository<Long, User> {
}
