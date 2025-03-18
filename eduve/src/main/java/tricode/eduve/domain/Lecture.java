package tricode.eduve.domain;


import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Lecture {

    @Id
    @Column(name = "lecture_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long LectureId;

    @Column(name = "lecture_name",  nullable = false)
    private String lectureName;

    @ManyToOne
    @JoinColumn(name = "teacher_id", nullable = false)
    private User teacher;

    @ManyToMany(mappedBy = "lectures", cascade = CascadeType.ALL)
    private List<User> students = new ArrayList<>();
}
