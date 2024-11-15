package tricode.eduve.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tricode.eduve.global.CreatedTimeEntity;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VoiceFile extends CreatedTimeEntity {
    @Id
    @Column(name = "void_file_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long voidFileId;

    @Column(nullable = false)
    private String voiceFileName;

    @Column(nullable = false)
    private String VoiceFileUrl;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
