package uz.service.course;

import org.springframework.data.domain.Pageable;
import uz.dto.request.CourseRequestDto;
import uz.service.BaseService;

public interface CourseService extends BaseService<CourseRequestDto, Pageable, Long>{
}
