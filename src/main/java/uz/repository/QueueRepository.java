package uz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.model.entity.queue.QueueEntity;

import java.util.List;

@Repository
public interface QueueRepository extends JpaRepository<QueueEntity, Long> {

    @Query(value = "select * from (select *, Rank() over(order by applieddate asc) from queues q inner join users u on q.user_id = u.id where q.course_id = :courseId and q.status = :status) as sub limit :limit", nativeQuery = true)
    List<QueueEntity> filterByCourseStatusLimitForGroups(@Param("courseId") Long courseId, @Param("status") String status, @Param("limit") int limit);
}
