package tricode.eduve.service;


import jakarta.transaction.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import tricode.eduve.domain.AllCharacter;
import tricode.eduve.domain.User;
import tricode.eduve.domain.UserCharacter;
import tricode.eduve.dto.request.User.JoinDTO;
import tricode.eduve.repository.AllCharacterRepository;
import tricode.eduve.repository.UserRepository;

@Service
@Transactional
public class StudentJoinService {

    private final UserRepository userRepository;
    private final UserCharacterService userCharacterService;
    private final AllCharacterRepository allCharacterRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public StudentJoinService(UserRepository userRepository, UserCharacterService userCharacterService,
                              AllCharacterRepository allCharacterRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {

        this.userRepository = userRepository;
        this.userCharacterService = userCharacterService;
        this.allCharacterRepository = allCharacterRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Transactional
    public User joinProcess(JoinDTO joinDTO) {

        System.out.println("회원가입 진입");
        String username = joinDTO.getUsername();
        String password = joinDTO.getPassword();
        String name = joinDTO.getName();
        String email = joinDTO.getEmail();
        String teacherUsername = joinDTO.getTeacherUsername();

        Boolean isExistUsername = userRepository.existsByUsername(username);
        Boolean isExistEmail = userRepository.existsByEmail(email);

        if (isExistUsername || isExistEmail) {
            throw new RuntimeException("이미 존재하는 사용자명 또는 이메일입니다.");
        }

        Boolean isExistTeacherId = userRepository.existsByUsername(teacherUsername);

        if (!isExistTeacherId) {
            throw new RuntimeException("존재하지 않는 선생님 아이디입니다.");
        }

        User data = new User();

        data.setUsername(username);
        data.setPassword(bCryptPasswordEncoder.encode(password));
        data.setName(name);
        data.setEmail(email);
        data.setTeacherUsername(teacherUsername);
        data.setRole("ROLE_Student");

        User savedUser = userRepository.save(data);
        System.out.println("savedUser = " + savedUser);

        // 기본 캐릭터 가져오기 (characterId = 1)
        AllCharacter defaultCharacter = allCharacterRepository.findByAllCharacterId(1L)
                .orElseThrow(() -> new RuntimeException("기본 캐릭터를 찾을 수 없습니다."));

        // 기본 UserCharacter 생성 및 저장
        UserCharacter defaultUserCharacter = userCharacterService.createDefaultUserCharacter(savedUser, defaultCharacter);

        System.out.println("UserCharacter 저장됨: " + defaultUserCharacter);

        return savedUser;
    }
}
