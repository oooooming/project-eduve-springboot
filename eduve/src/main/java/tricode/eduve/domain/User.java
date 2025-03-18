package tricode.eduve.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import tricode.eduve.global.CreatedTimeEntity;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User extends CreatedTimeEntity {

    @Id
    @Column(name = "user_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String email;

    @ManyToMany
    @JoinTable(
            name = "user_lecture", // 중간 테이블 이름
            joinColumns = @JoinColumn(name = "user_id"), // 현재 엔티티의 외래키
            inverseJoinColumns = @JoinColumn(name = "lecture_id") // 반대편 엔티티의 외래키
    )
    private List<Lecture> lectures = new ArrayList<>();

}
