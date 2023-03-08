package uz.service.user;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.dto.Response;
import uz.dto.response.RestAPIResponse;
import uz.dto.request.RegisterRequestDto;
import uz.dto.response.UserResponse;
import uz.model.entity.user.UserEntity;
import uz.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService, Response {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public RestAPIResponse create(RegisterRequestDto request) {
        return null;
    }

    @Override
    public RestAPIResponse update(Long id, RegisterRequestDto request) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id - " + id));
        modelMapper.map(request, userEntity);
        userEntity.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(userEntity);
        return RestAPIResponse.builder()
                .success(true)
                .message(SUCCESSFULLY_UPDATED)
                .statusCode(HttpStatus.OK.value())
                .build();
    }

    @Override
    public RestAPIResponse delete(Long id) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id - " + id));
        userRepository.delete(userEntity);
        return RestAPIResponse.builder()
                .message(SUCCESSFULLY_DELETED)
                .success(true)
                .statusCode(HttpStatus.NO_CONTENT.value())
                .build();
    }

    @Override
    public RestAPIResponse get(Long id) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id - " + id));
        UserResponse userResponse = modelMapper.map(userEntity, UserResponse.class);
        return RestAPIResponse.builder()
                .message(USER)
                .success(true)
                .statusCode(200)
                .data(userResponse)
                .build();
    }

    @Override
    public RestAPIResponse getAll(Pageable pageable) {
        Page<UserEntity> userEntityPage = userRepository.findAll(pageable);
        final List<UserResponse> list = userEntityPage.getContent().size() > 0  ?
                userEntityPage.getContent().stream().map(u -> modelMapper.map(u,UserResponse.class)).toList()
                : new ArrayList<>();
        PageImpl<UserResponse> userResponsePage = new PageImpl<>(list, userEntityPage.getPageable(), userEntityPage.getTotalPages());
        return RestAPIResponse.builder()
                .message(DATA_LIST)
                .statusCode(200)
                .success(true)
                .data(userResponsePage)
                .build();
    }
}
