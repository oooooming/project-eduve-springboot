package tricode.eduve.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tricode.eduve.domain.Preference;

public interface PreferenceRepository extends JpaRepository<Long, Preference> {
}
