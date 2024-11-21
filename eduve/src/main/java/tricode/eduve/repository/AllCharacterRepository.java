package tricode.eduve.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tricode.eduve.domain.AllCharacter;

public interface AllCharacterRepository extends JpaRepository<AllCharacter, Long> {
}
