package tricode.eduve.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tricode.eduve.domain.User;
import tricode.eduve.repository.UserRepository;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User findById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
    }
}
