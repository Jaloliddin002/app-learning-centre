package uz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import uz.model.entity.user.UserEntity;
import uz.model.enums.Role;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByPhoneNumber(String phoneNumber);

    Optional<UserEntity> findByIdAndRole(Long id, Role role);
}
