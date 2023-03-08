package uz.service.group;


import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.dto.Response;
import uz.dto.request.GroupRequestDto;
import uz.dto.response.GroupResponseDto;
import uz.dto.response.RestAPIResponse;
import uz.model.entity.course.CourseEntity;
import uz.model.entity.group.GroupEntity;
import uz.model.entity.user.UserEntity;
import uz.model.enums.Role;
import uz.repository.CourseRepository;
import uz.repository.GroupRepository;
import uz.repository.UserRepository;
import uz.service.queue.QueueService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GroupServiceImp implements GroupService, Response {
    private final GroupRepository groupRepository;
    private final ModelMapper modelMapper;
    private final QueueService queueService;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;

    @Override
    public RestAPIResponse create(GroupRequestDto requestDto) {
        boolean exists = groupRepository.existsByName(requestDto.getName());
        String message = "";
        int statusCode = 0;
        if (!exists) {
            Optional<CourseEntity> optionalCourse = courseRepository.findById(requestDto.getCourseId());
            if (optionalCourse.isPresent()) {
                Optional<UserEntity> optionalUser = userRepository.findByIdAndRole(requestDto.getTeacherId(), Role.TEACHER);
                if (optionalUser.isPresent()) {
                    List<UserEntity> students =
                            queueService.getUsers(requestDto.getCourseId(), "PENDING", requestDto.getMembers());
                    if (students != null) {
                        GroupEntity groupEntity = modelMapper.map(requestDto, GroupEntity.class);
                        groupEntity.setCourse(optionalCourse.get());
                        groupEntity.setTeacher(optionalUser.get());
                        groupEntity.setUserEntities(students);
                        groupEntity.setStartDate(LocalDate.now());
                        groupRepository.save(groupEntity);
                        return RestAPIResponse.builder()
                                .message(SUCCESSFULLY_SAVED)
                                .success(true)
                                .statusCode(201)
                                .build();
                    }
                }else {
                    message = "Teacher" + NOT_FOUND;
                    statusCode = 404;
                }
            }else {
                message = COURSE + NOT_FOUND;
                statusCode = 404;
            }
        }else {
            message = GROUP + ALREADY_EXIST;
            statusCode = 400;
        }
        return RestAPIResponse.builder()
                .message(message)
                .success(false)
                .statusCode(statusCode)
                .build();
    }

    @Override
    public RestAPIResponse update(Long id, GroupRequestDto groupRequestDto) {
        Optional<GroupEntity> optionalGroup = groupRepository.findById(id);
        if (optionalGroup.isPresent()) {
            GroupEntity groupEntity = optionalGroup.get();
            modelMapper.map(groupRequestDto, groupEntity);
            groupRepository.save(groupEntity);
            return RestAPIResponse.builder()
                    .statusCode(202)
                    .message(SUCCESSFULLY_UPDATED)
                    .success(true)
                    .build();
        }
        return RestAPIResponse.builder()
                .message(GROUP + NOT_FOUND)
                .success(false)
                .statusCode(404)
                .build();
    }

    @Override
    public RestAPIResponse delete(Long id) {
        Optional<GroupEntity> optionalGroup = groupRepository.findById(id);
        if (optionalGroup.isPresent()) {
            groupRepository.delete(optionalGroup.get());
            return RestAPIResponse.builder()
                    .message(SUCCESSFULLY_DELETED)
                    .statusCode(202)
                    .success(true)
                    .build();
        }
        return RestAPIResponse.builder()
                .message(GROUP + NOT_FOUND)
                .success(false)
                .statusCode(404)
                .build();
    }

    @Override
    public RestAPIResponse get(Long id) {
        Optional<GroupEntity> optionalGroup = groupRepository.findById(id);
        if (optionalGroup.isPresent()) {
            GroupEntity groupEntity = optionalGroup.get();
            GroupResponseDto groupResponseDto = modelMapper.map(groupEntity, GroupResponseDto.class);
            List<Long> studentsId = groupEntity.getUserEntities().stream()
                    .map(UserEntity :: getId)
                    .toList();
            groupResponseDto.setStudentsId(studentsId);
            return RestAPIResponse.builder()
                    .message(GROUP)
                    .success(true)
                    .data(groupResponseDto)
                    .statusCode(200)
                    .build();
        }
        return RestAPIResponse.builder()
                .message(GROUP + NOT_FOUND)
                .success(false)
                .statusCode(404)
                .build();
    }

    @Override
    public RestAPIResponse getAll(Pageable pageable) {
        Page<GroupEntity> groupEntities = groupRepository.findAll(pageable);
        final List<GroupResponseDto> list = groupEntities.getContent().size() > 0 ?
                groupEntities.getContent().stream().map(u -> modelMapper.map(u, GroupResponseDto.class)).toList()
                : new ArrayList<>();
        PageImpl<GroupResponseDto> groups = new PageImpl<>(list, groupEntities.getPageable(), groupEntities.getTotalPages());
        return RestAPIResponse.builder()
                .message(DATA_LIST)
                .success(true)
                .statusCode(200)
                .data(groups)
                .build();
    }
}
