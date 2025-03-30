package tricode.eduve.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import tricode.eduve.domain.*;
import tricode.eduve.dto.UserCharacterPreferenceDto;
import tricode.eduve.dto.UserCharacterPreferenceRequestDto;
import tricode.eduve.repository.UserCharacterRepository;
import tricode.eduve.repository.UserRepository;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class UserCharacterService {

    private final UserCharacterRepository userCharacterRepository;
    private final UserRepository userRepository;

    // userId로 캐릭터 설정 업데이트
    public UserCharacterPreferenceDto updateUserCharacter(Long userId, UserCharacterPreferenceRequestDto requestDto) {
        UserCharacter userCharacter = getUserCharacter(userId);

        if (requestDto.getUserCharacterName() != null) {
            userCharacter.setUserCharacterName(requestDto.getUserCharacterName());
        }

        // 기존 Preference 객체를 가져와서 업데이트
        Preference preference = userCharacter.getPreference();

        if (requestDto.getTone() != null) {
            // Tone 값이 Enum에 존재하는지 확인
            try {
                Tone toneEnum = Tone.valueOf(requestDto.getTone());  // ToneEnum을 enum으로 변환
                preference.setTone(toneEnum);  // 유효한 값이면 설정
            } catch (IllegalArgumentException e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid tone value");  // 유효하지 않으면 400 오류
            }
        }

        if (requestDto.getDescriptionLevel() != null) {
            // DescriptionLevel 값이 Enum에 존재하는지 확인
            try {
                DescriptionLevel descriptionLevelEnum = DescriptionLevel.valueOf(requestDto.getDescriptionLevel());  // DescriptionLevelEnum을 enum으로 변환
                preference.setDescriptionLevel(descriptionLevelEnum);  // 유효한 값이면 설정
            } catch (IllegalArgumentException e) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid descriptionlevel value");  // 유효하지 않으면 400 오류
            }
        }

        userCharacterRepository.save(userCharacter);
        return UserCharacterPreferenceDto.from(userCharacter);  // UserCharacter -> UserCharacterPreferenceDto
    }

    // userId로 캐릭터 설정 조회
    public UserCharacterPreferenceDto getUserCharacterPreference(Long userId) {
        UserCharacter userCharacter = getUserCharacter(userId);
        return UserCharacterPreferenceDto.from(userCharacter);  // UserCharacter -> UserCharacterPreferenceDto
    }

    public UserCharacter getUserCharacter(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new RuntimeException("not found user"));

        return userCharacterRepository.findByUser(user)
                .orElseThrow(()-> new RuntimeException("not found userCharacter"));
    }
}
