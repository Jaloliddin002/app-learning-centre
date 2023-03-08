package uz.model.entity.course;

import jakarta.persistence.*;
import lombok.*;
import uz.audit.Auditable;
import uz.model.entity.user.UserEntity;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "courses")
public class CourseEntity extends Auditable<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    private double price;

    private int duration;

    private String description;

    @OneToMany
    private List<UserEntity> teachers;
}
