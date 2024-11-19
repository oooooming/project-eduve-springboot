package tricode.eduve.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tricode.eduve.domain.File;

public interface FileRepository extends JpaRepository <Long, File> {
}
