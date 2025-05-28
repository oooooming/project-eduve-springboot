package tricode.eduve.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tricode.eduve.domain.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);

    //username을 받아 DB 테이블에서 회원을 조회하는 메소드 작성
    User findByUsername(String username);
    Optional<User> findByUserId(Long userId);

    @Query("SELECT u FROM User u WHERE u.username = :username AND u.role = 'ROLE_Teacher'")
    Optional<User> findByTeacherUsername(@Param("username") String username);
}