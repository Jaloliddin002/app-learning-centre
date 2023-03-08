package uz.service.course;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.dto.Response;
import uz.dto.request.CourseRequestDto;
import uz.dto.response.CourseResponseDto;
import uz.dto.response.RestAPIResponse;
import uz.model.entity.course.CourseEntity;
import uz.repository.CourseRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CourseServiceImp implements CourseService, Response {

    private final CourseRepository courseRepository;
    private final ModelMapper modelMapper;

    @Override
    public RestAPIResponse create(CourseRequestDto courseRequestDto) {
        boolean exists = courseRepository.existsByName(courseRequestDto.getName());
        if (exists) {
            return RestAPIResponse.builder()
                    .message(COURSE + ALREADY_EXIST)
                    .success(false)
                    .statusCode(400)
                    .build();
        }
        CourseEntity courseEntity = modelMapper.map(courseRequestDto, CourseEntity.class);
        courseEntity.setTeachers(new ArrayList<>());
        courseRepository.save(courseEntity);
        return RestAPIResponse.builder()
                .message(SUCCESSFULLY_SAVED)
                .success(true)
                .statusCode(HttpStatus.CREATED.value())
                .build();
    }

    @Override
    public RestAPIResponse update(Long id, CourseRequestDto courseRequestDto) {
        Optional<CourseEntity> optionalCourse = courseRepository.findById(id);
        if (optionalCourse.isEmpty()) {
            return RestAPIResponse.builder()
                    .message(COURSE + NOT_FOUND)
                    .success(false)
                    .statusCode(404)
                    .build();
        }
        CourseEntity courseEntity = optionalCourse.get();
        modelMapper.map(courseRequestDto, courseEntity);
        courseRepository.save(courseEntity);
        return RestAPIResponse.builder()
                .message(SUCCESSFULLY_UPDATED)
                .success(true)
                .statusCode(202)
                .build();
    }

    @Override
    public RestAPIResponse delete(Long id) {
        Optional<CourseEntity> optionalCourse = courseRepository.findById(id);
        if (optionalCourse.isEmpty()) {
            return RestAPIResponse.builder()
                    .message(COURSE + NOT_FOUND)
                    .success(false)
                    .statusCode(404)
                    .build();
        }
        CourseEntity courseEntity = optionalCourse.get();
        courseRepository.delete(courseEntity);
        return RestAPIResponse.builder()
                .message(SUCCESSFULLY_DELETED)
                .success(true)
                .statusCode(204)
                .build();

    }

    @Override
    public RestAPIResponse get(Long id) {
        Optional<CourseEntity> optionalCourse = courseRepository.findById(id);
        if (optionalCourse.isEmpty()) {
            return RestAPIResponse.builder()
                    .message(COURSE + NOT_FOUND)
                    .success(false)
                    .statusCode(404)
                    .build();
        }
        CourseEntity courseEntity = optionalCourse.get();
        CourseResponseDto courseResponseDto = modelMapper.map(courseEntity, CourseResponseDto.class);
        return RestAPIResponse.builder()
                .message(COURSE)
                .success(true)
                .statusCode(200)
                .data(courseResponseDto)
                .build();
    }

    @Override
    public RestAPIResponse getAll(Pageable pageable) {
        Page<CourseEntity> courseEntities = courseRepository.findAll(pageable);
        List<CourseResponseDto> courseResponseDtoList = courseEntities.getContent().size() > 0 ?
                courseEntities.getContent().stream().map(u -> modelMapper.map(u, CourseResponseDto.class)).toList() :
                new ArrayList<>();
        PageImpl<CourseResponseDto> responsePage = new PageImpl<>(courseResponseDtoList, courseEntities.getPageable(), courseEntities.getTotalPages());
        return RestAPIResponse.builder()
                .message(DATA_LIST)
                .success(true)
                .statusCode(200)
                .data(responsePage)
                .build();
    }
}
