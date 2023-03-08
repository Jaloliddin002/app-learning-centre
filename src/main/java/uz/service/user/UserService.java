package uz.service.user;

import org.springframework.data.domain.Pageable;
import uz.dto.request.RegisterRequestDto;
import uz.service.BaseService;


public interface UserService extends BaseService<RegisterRequestDto, Pageable, Long> {
}
