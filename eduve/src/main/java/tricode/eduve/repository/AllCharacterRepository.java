package tricode.eduve.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tricode.eduve.domain.AllCharacter;

@Repository
public interface AllCharacterRepository extends JpaRepository<AllCharacter, Long> {
}
