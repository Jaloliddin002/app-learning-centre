package uz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.model.entity.course.CourseEntity;

@Repository
public interface CourseRepository extends JpaRepository<CourseEntity, Long> {

    boolean existsByName(String name);
}
