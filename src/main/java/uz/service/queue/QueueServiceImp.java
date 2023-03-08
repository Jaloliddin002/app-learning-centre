package uz.service.queue;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import uz.dto.Response;
import uz.dto.request.QueueRequestDto;
import uz.dto.response.QueueResponse;
import uz.dto.response.RestAPIResponse;
import uz.model.entity.course.CourseEntity;
import uz.model.entity.queue.QueueEntity;
import uz.model.entity.user.UserEntity;
import uz.model.enums.Status;
import uz.repository.CourseRepository;
import uz.repository.QueueRepository;
import uz.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class QueueServiceImp implements QueueService, Response{

    private final QueueRepository queueRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;

    @Override
    public RestAPIResponse create(QueueRequestDto queueRequestDto) {
        Optional<CourseEntity> optionalCourse = courseRepository.findById(queueRequestDto.getCourseId());
        if (optionalCourse.isPresent()) {
            Optional<UserEntity> optionalUser = userRepository.findById(queueRequestDto.getUserId());
            if (optionalUser.isPresent()) {
                QueueEntity queue = new QueueEntity();
                queue.setCourse(optionalCourse.get());
                queue.setUser(optionalUser.get());
                queue.setAppliedDate(LocalDateTime.now());
                queue.setStatus(Status.PENDING);
                queueRepository.save(queue);
                return RestAPIResponse.builder()
                        .message(SUCCESSFULLY_SAVED)
                        .success(true)
                        .statusCode(201)
                        .build();
            }
        }
        return RestAPIResponse.builder()
                .message(NOT_FOUND)
                .success(false)
                .statusCode(404)
                .build();
    }

    @Override
    public RestAPIResponse update(Long id, QueueRequestDto queueRequestDto) {
        Optional<QueueEntity> optionalQueue = queueRepository.findById(id);
        if (optionalQueue.isPresent()){
            QueueEntity queue = optionalQueue.get();
            queue.setStatus(Status.valueOf(queueRequestDto.getStatus()));
            queueRepository.save(queue);
            return RestAPIResponse.builder()
                    .message(SUCCESSFULLY_UPDATED)
                    .success(true)
                    .statusCode(204)
                    .build();
        }
        return RestAPIResponse.builder()
                .message(QUEUE + NOT_FOUND)
                .success(false)
                .statusCode(404)
                .build();
    }

    @Override
    public RestAPIResponse delete(Long id) {
        Optional<QueueEntity> optionalQueue = queueRepository.findById(id);
        if (optionalQueue.isPresent()) {
            QueueEntity queue = optionalQueue.get();
            queueRepository.delete(queue);
            return RestAPIResponse.builder()
                    .message(SUCCESSFULLY_DELETED)
                    .statusCode(204)
                    .success(true)
                    .build();
        }
        return RestAPIResponse.builder()
                .message(QUEUE + NOT_FOUND)
                .success(false)
                .statusCode(404)
                .build();
    }

    @Override
    public RestAPIResponse get(Long id) {
        Optional<QueueEntity> optionalQueue = queueRepository.findById(id);
        if (optionalQueue.isPresent()) {
            QueueResponse queueResponse = modelMapper.map(optionalQueue.get(), QueueResponse.class);
            return RestAPIResponse.builder()
                    .message(QUEUE)
                    .success(true)
                    .statusCode(200)
                    .data(queueResponse)
                    .build();
        }
        return RestAPIResponse.builder()
                .message(NOT_FOUND)
                .success(false)
                .statusCode(404)
                .build();
    }

    @Override
    public RestAPIResponse getAll(Pageable pageable) {
        Page<QueueEntity> queueEntities = queueRepository.findAll(pageable);
        List<QueueResponse> list = queueEntities.getContent().size() > 0 ?
                queueEntities.getContent().stream().map(u -> modelMapper.map(u, QueueResponse.class)).toList() :
                new ArrayList<>();
        PageImpl<QueueResponse> queueResponses = new PageImpl<>(list, queueEntities.getPageable(), queueEntities.getTotalPages());
        return RestAPIResponse.builder()
                .message(DATA_LIST)
                .success(true)
                .statusCode(200)
                .data(queueResponses)
                .build();
    }

    @Override
    public List<UserEntity> getUsers(Long courseId, String status, int limit) {
        List<QueueEntity> queueEntities = queueRepository.filterByCourseStatusLimitForGroups(courseId, status, limit);
        if (queueEntities.size() != 0) {
            return queueEntities
                    .stream().map(u -> {
                         u.setStatus(Status.ACCEPTED);
                         queueRepository.save(u);
                         return u.getUser();
                    }).toList();
        }
        return null;
    }
}
