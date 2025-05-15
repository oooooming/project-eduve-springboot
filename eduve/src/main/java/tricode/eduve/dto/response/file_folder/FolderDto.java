package tricode.eduve.dto.response.file_folder;

import lombok.Getter;
import lombok.Setter;
import tricode.eduve.domain.Folder;
import tricode.eduve.domain.User;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
@Setter
public class FolderDto {
    private Long FolderId;
    private String FolderName;
    private String FolderOwner;
    private String path;
    private Long parentId;
    private List<FolderDto> subFolders;
    private List<FileDto> files;

    public static FolderDto fromEntity(Folder folder, User user, User teacher) {
        FolderDto dto = new FolderDto();
        dto.setFolderId(folder.getFolderId());
        dto.setFolderName(folder.getName());
        dto.setFolderOwner(folder.getUser().getName());
        dto.setPath(folder.getPath());
        dto.setParentId(folder.getParentFolder() != null ? folder.getParentFolder().getFolderId() : null);


        // 유저 또는 선생님에 해당하는 하위 폴더만 포함
        dto.setSubFolders(
                folder.getSubFolders().stream()
                        .filter(subFolder -> {
                            User folderOwner = subFolder.getUser();
                            return Objects.equals(folderOwner, user) || Objects.equals(folderOwner, teacher);
                        })
                        .map(subFolder -> fromEntity(subFolder, user, teacher))
                        .collect(Collectors.toList())
        );

        // 유저 또는 선생님에 해당하는 파일만 포함
        dto.setFiles(
                folder.getFiles().stream()
                        .filter(file -> {
                            User fileOwner = file.getUser();
                            return Objects.equals(fileOwner, user) || Objects.equals(fileOwner, teacher);
                        })
                        .map(FileDto::fromEntity)
                        .collect(Collectors.toList())
        );

        return dto;
    }


    public static FolderDto fromEntity(Folder folder) {
        FolderDto dto = new FolderDto();
        dto.setFolderId(folder.getFolderId());
        dto.setFolderName(folder.getName());
        dto.setFolderOwner(folder.getUser().getName());
        dto.setPath(folder.getPath());
        dto.setParentId(folder.getParentFolder() != null ? folder.getParentFolder().getFolderId() : null);
        return dto;
    }
}
