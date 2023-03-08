package uz.service.group;

import org.springframework.data.domain.Pageable;
import uz.dto.request.GroupRequestDto;
import uz.service.BaseService;

public interface GroupService extends BaseService<GroupRequestDto, Pageable, Long> {

}
