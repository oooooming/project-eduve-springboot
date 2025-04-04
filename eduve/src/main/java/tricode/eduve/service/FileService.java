package tricode.eduve.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import tricode.eduve.domain.File;
import tricode.eduve.dto.response.FileResponseDto;
import tricode.eduve.repository.FileRepository;

@Service
@RequiredArgsConstructor
@Transactional
public class FileService {
    private final FileRepository fileRepository;

    public FileResponseDto getFileById(Long fileId) {
        // 파일 조회 (없으면 예외 발생)
        File file = fileRepository.findById(fileId)
                .orElseThrow(() -> new IllegalArgumentException("파일을 찾을 수 없습니다."));

        // Dto 변환 후 반환
        return FileResponseDto.from(file);
    }
}
