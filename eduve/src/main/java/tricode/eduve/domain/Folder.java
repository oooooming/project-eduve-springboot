package tricode.eduve.domain;

import jakarta.persistence.*;
import lombok.*;
import tricode.eduve.global.CreatedTimeEntity;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Setter
public class Folder extends CreatedTimeEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long folderId;

    @Column(nullable = false)
    private String name;


    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Folder parentFolder;


    private String path;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false) // User 연결
    private User user;


    @OneToMany(mappedBy = "parentFolder", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Folder> subFolders = new ArrayList<>();


    @OneToMany(mappedBy = "folder", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<File> files = new ArrayList<>();


    public Folder(String name, User user) {
        this.name = name;
        this.user = user;
    }


    public void updatePath() {
        if (parentFolder == null) {
            this.path = "/" + this.name;
        } else {
            this.path = parentFolder.getPath() + "/" + this.name;
        }
    }
}
