package tricode.eduve.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tricode.eduve.domain.VoiceFile;

public interface VoiceFileRepository extends JpaRepository<VoiceFile, Long> {
}
