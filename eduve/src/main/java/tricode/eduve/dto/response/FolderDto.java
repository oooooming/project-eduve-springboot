package tricode.eduve.dto.response;

import lombok.Getter;
import lombok.Setter;
import tricode.eduve.domain.Folder;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
public class FolderDto {
    private Long FolderId;
    private String FolderName;
    private String path;
    private Long parentId;
    private List<FolderDto> subFolders;
    private List<FileDto> files;

    public static FolderDto fromEntity(Folder folder) {
        FolderDto dto = new FolderDto();
        dto.setFolderId(folder.getFolderId());
        dto.setFolderName(folder.getName());
        dto.setPath(folder.getPath());
        dto.setParentId(folder.getParentFolder() != null ? folder.getParentFolder().getFolderId() : null);
        dto.setSubFolders(folder.getSubFolders().stream().map(FolderDto::fromEntity).collect(Collectors.toList()));
        dto.setFiles(folder.getFiles().stream().map(FileDto::fromEntity).collect(Collectors.toList()));
        return dto;
    }
}
