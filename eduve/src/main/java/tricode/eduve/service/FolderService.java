package tricode.eduve.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import tricode.eduve.domain.File;
import tricode.eduve.domain.Folder;
import tricode.eduve.domain.User;
import tricode.eduve.dto.response.file_folder.FolderDto;
import tricode.eduve.dto.response.file_folder.RootFolderDto;
import tricode.eduve.repository.FileRepository;
import tricode.eduve.repository.FolderRepository;
import tricode.eduve.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class FolderService {

    private final FolderRepository folderRepository;
    private final FileRepository fileRepository;
    private final UserRepository userRepository;


    public FolderDto createFolder(String name, Long parentId, Long userId) {
        User user = findUserById(userId);

        Folder folder = new Folder(name, user);

        if (parentId != null) {
            Folder parent = folderRepository.findById(parentId)
                    .orElseThrow(() -> new RuntimeException("부모 폴더를 찾을 수 없습니다."));
            folder.setParentFolder(parent);
        } else {
            folder.setParentFolder(null); // 최상위 폴더
        }

        folder.updatePath();
        return FolderDto.fromEntity(folderRepository.save(folder));
    }

    public FolderDto getFolder(Long folderId, Long userId) {
        Folder folder =  findFolderById(folderId);

        User user = findUserById(userId);
        //임시 teacher
        User teacher = new User();

        // 연결된 선생님 찾기
        /*
        User teacher = userRepository.findTeacherByStudent0(user)
                    .orElseThrow(() -> new RuntimeException("선생님을 찾을 수 없습니다."));
         */
        return FolderDto.fromEntity(folder, user, teacher);
    }

    /*
    public List<FolderDto> getAllFolders(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));

        return folderRepository.findAll().stream()
                .map(FolderDto::fromEntity)
                .collect(Collectors.toList());
    }
    */

    // 폴더 삭제
    public void deleteFolder(Long folderId) {
        folderRepository.deleteById(folderId);
    }

    public String getFilePath(Long fileId) {
        File file = fileRepository.findById(fileId)
                .orElseThrow(() -> new RuntimeException("파일을 찾을 수 없습니다."));
        return file.getFullPath(); // 저장된 path가 아닌 동적 계산된 path 반환
    }

    //최상위 폴더 리스트 조회(선생님+자기꺼)
    public List<RootFolderDto> getRootFoldersByUser(Long userId) {
        User user = findUserById(userId);

        // 자기 자신 + (optional) 선생님 포함할 수 있도록 변경
        List<User> accessibleUsers = new ArrayList<>();
        accessibleUsers.add(user);


        // 연결된 선생님이 있다면 추가
        /*
        userRepository.findTeacherIdByStudentId(userId).ifPresent(teacherId -> {
            User teacher = userRepository.findById(teacherId)
                    .orElseThrow(() -> new RuntimeException("선생님을 찾을 수 없습니다."));
            accessibleUsers.add(teacher);
        });
        */

        // 이 유저들에 해당하는 루트 폴더 조회
        return folderRepository.findByUserInAndParentFolderIsNull(accessibleUsers).stream()
                .map(RootFolderDto::fromEntity)
                .collect(Collectors.toList());
    }

    //폴더 이름 수정
    public ResponseEntity<FolderDto> updateFolder(Long folderId, String newFolderName, Long userId) {
        User user = findUserById(userId);
        Folder folder =  findFolderById(folderId);

        // 수정 권한 확인
        if(!Objects.equals(user.getUserId(), folder.getUser().getUserId())) {
            throw new RuntimeException("수정 권한이 없습니다.");
        }

        folder.setName(newFolderName);
        folderRepository.save(folder);
        return ResponseEntity.ok(FolderDto.fromEntity(folder));
    }

    // 파일 path 수정
    public String updateFolderPath(Long folderId, Long newParentFolderId, Long userId) {
        User user = findUserById(userId);
        Folder folder =  findFolderById(folderId);

        // 수정 권한 확인
        if(!Objects.equals(user.getUserId(), folder.getUser().getUserId())) {
            throw new RuntimeException("수정 권한이 없습니다.");
        }

        Folder newParentFolder =  folderRepository.findById(newParentFolderId)
                .orElseThrow(() -> new RuntimeException("새로운 상위 폴더를 찾을 수 없습니다."));

        folder.setParentFolder(newParentFolder);
        folder.updatePath();
        folderRepository.save(folder);

        return folder.getPath();
    }

    public User findUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("유저를 찾을 수 없습니다."));
        return user;
    }
    public Folder findFolderById(Long folderId) {
        Folder folder =  folderRepository.findById(folderId)
                .orElseThrow(() -> new RuntimeException("폴더를 찾을 수 없습니다."));
        return folder;
    }
}

