package uz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.model.entity.group.GroupEntity;

@Repository
public interface GroupRepository extends JpaRepository<GroupEntity, Long> {

    boolean existsByName(String name);
}
