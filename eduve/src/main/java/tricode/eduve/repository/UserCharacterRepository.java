package tricode.eduve.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tricode.eduve.domain.UserCharacter;

public interface UserCharacterRepository extends JpaRepository<UserCharacter, Long> {
}
