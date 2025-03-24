package tricode.eduve.service;

import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tricode.eduve.domain.File;
import tricode.eduve.domain.FileType;
import tricode.eduve.domain.User;
import tricode.eduve.repository.FileRepository;
import tricode.eduve.repository.UserRepository;

import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FileUploadService {
    private final AmazonS3Client amazonS3Client;
    private final FileRepository fileRepository;
    private final UserRepository userRepository;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${cloud.aws.region.static}")
    private String region;

    public String uploadFile(MultipartFile file, Long userId) throws IOException {
        // 파일 이름과 URL 생성
        String fileName = file.getOriginalFilename();
        String fileUrl = "https://" + bucket + ".s3." + region + ".amazonaws.com/" + fileName;

        // S3에 파일 업로드
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());

        amazonS3Client.putObject(bucket, fileName, file.getInputStream(), metadata);

        // 파일 정보를 File 엔티티에 저장
        Optional<User> userOptional = userRepository.findByUserId(userId);
        if (userOptional.isEmpty()) {
            throw new IOException("User not found");
        }

        User user = userOptional.get();

        // File 엔티티 객체 생성
        File fileEntity = File.builder()
                .fileName(fileName)
                .fileType(FileType.TEXT)  // 파일 유형을 TEXT로 설정 (필요에 따라 다른 유형을 설정)
                .fileUrl(fileUrl)
                .user(user)
                .build();

        // 파일 엔티티를 DB에 저장
        fileRepository.save(fileEntity);

        return fileUrl; // 성공적으로 파일 URL 반환
    }
}
