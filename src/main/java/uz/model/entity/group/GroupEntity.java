package uz.model.entity.group;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import jakarta.persistence.*;
import lombok.*;
import uz.audit.Auditable;
import uz.model.entity.course.CourseEntity;
import uz.model.entity.user.UserEntity;
import uz.model.enums.CourseType;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "groups")
public class GroupEntity extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate startDate;

    private LocalTime startTime;

    private LocalTime endTime;

    @ManyToOne
    private CourseEntity course;

    @ManyToOne
    private UserEntity teacher;

    private int members;

    @Enumerated(EnumType.STRING)
    private CourseType courseType;

    @OneToMany(fetch = FetchType.EAGER)
    private List<UserEntity> userEntities;
}
