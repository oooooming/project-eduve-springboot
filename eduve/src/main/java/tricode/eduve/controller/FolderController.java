package tricode.eduve.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tricode.eduve.domain.Folder;
import tricode.eduve.dto.FolderDto;
import tricode.eduve.service.FolderService;

import java.util.List;

@RestController
@RequestMapping("/folders")
@RequiredArgsConstructor
public class FolderController {

    private final FolderService folderService;

    // 폴더 생성
    @PostMapping
    public ResponseEntity<FolderDto> createFolder(
            @RequestParam String folderName,
            @RequestParam(required = false) Long parentId,
            @RequestParam Long userId) {
        return ResponseEntity.ok(folderService.createFolder(folderName, parentId, userId));
    }

    // 폴더 하나 조회
    @GetMapping("/{folderId}")
    public ResponseEntity<FolderDto> getFolder(@PathVariable Long folderId) {
        return ResponseEntity.ok(folderService.getFolder(folderId));
    }

    // 모든 폴더 구하는
    @GetMapping
    public ResponseEntity<List<FolderDto>> getAllFolders() {
        return ResponseEntity.ok(folderService.getAllFolders());
    }

    // 폴더 삭제
    @DeleteMapping("/{folderId}")
    public ResponseEntity<Void> deleteFolder(@PathVariable Long folderId) {
        folderService.deleteFolder(folderId);
        return ResponseEntity.noContent().build();
    }

    // 파일 path 구하기
    @GetMapping("/files/{fileId}/path")
    public ResponseEntity<String> getFilePath(@PathVariable Long fileId) {
        return ResponseEntity.ok(folderService.getFilePath(fileId));
    }
}
