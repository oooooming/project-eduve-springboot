package tricode.eduve.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tricode.eduve.dto.response.FileResponseDto;
import tricode.eduve.service.FileService;
import tricode.eduve.service.FileUploadService;

import java.io.IOException;

@RestController
@RequestMapping("/resources/file")
@RequiredArgsConstructor
public class FileController {

    private final FileUploadService fileUploadService;
    private final FileService fileService;

    // 일반 파일 업로드
    @PostMapping("/text")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("userId") Long userId) {
        try {
            String fileUrl = fileUploadService.uploadFileToS3(file, userId);
            return ResponseEntity.ok(fileUrl); // 성공적으로 파일 URL 반환
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("File upload failed: " + e.getMessage());
        }
    }

    // 파일 조회
    @GetMapping("/{fileId}")
    public ResponseEntity<FileResponseDto> getFile(@PathVariable Long fileId) {
        FileResponseDto fileResponseDto = fileService.getFileById(fileId);
        return ResponseEntity.ok(fileResponseDto);
    }
}