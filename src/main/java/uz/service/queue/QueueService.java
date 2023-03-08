package uz.service.queue;

import org.springframework.data.domain.Pageable;
import uz.dto.request.QueueRequestDto;
import uz.model.entity.user.UserEntity;
import uz.service.BaseService;
import java.util.List;

public interface QueueService extends BaseService<QueueRequestDto, Pageable, Long> {

    List<UserEntity> getUsers(Long courseId, String status, int limit);

}
