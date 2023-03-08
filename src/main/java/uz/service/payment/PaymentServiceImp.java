package uz.service.payment;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import uz.dto.Response;
import uz.dto.request.PaymentRequestDto;
import uz.dto.response.GroupResponseDto;
import uz.dto.response.PaymentResponseDto;
import uz.dto.response.RestAPIResponse;
import uz.model.entity.payment.PaymentEntity;
import uz.model.entity.user.UserEntity;
import uz.repository.PaymentRepository;
import uz.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaymentServiceImp implements PaymentService, Response {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PaymentRepository paymentRepository;

    @Override
    public RestAPIResponse create(PaymentRequestDto requestDto) {
        Optional<UserEntity> optionalUser = userRepository.findById(requestDto.getUserId());
        if (optionalUser.isPresent()) {
            UserEntity userEntity = optionalUser.get();
            userEntity.setBalance(userEntity.getBalance() + requestDto.getAmount());
            PaymentEntity paymentEntity = new PaymentEntity();
            paymentEntity.setAmount(requestDto.getAmount());
            paymentEntity.setUser(userEntity);
            paymentEntity.setAppliedDate(LocalDateTime.now());
            userRepository.save(userEntity);
            paymentRepository.save(paymentEntity);
            return RestAPIResponse.builder()
                    .success(true)
                    .message(SUCCESSFULLY_SAVED)
                    .statusCode(201)
                    .build();
        }
        return RestAPIResponse.builder()
                .success(false)
                .statusCode(404)
                .message(USER + NOT_FOUND)
                .build();
    }

    @Override
    public RestAPIResponse update(Long id, PaymentRequestDto paymentRequestDto) {
        Optional<PaymentEntity> optionalPayment = paymentRepository.findById(id);
        if (optionalPayment.isPresent()) {
            PaymentEntity paymentEntity = optionalPayment.get();
            modelMapper.map(paymentRequestDto, paymentEntity);
            paymentRepository.save(paymentEntity);
            return RestAPIResponse.builder()
                    .success(true)
                    .message(SUCCESSFULLY_UPDATED)
                    .statusCode(202)
                    .build();
        }
        return RestAPIResponse.builder()
                .success(false)
                .statusCode(404)
                .message(PAYMENT + NOT_FOUND)
                .build();
    }

    @Override
    public RestAPIResponse delete(Long id) {
        Optional<PaymentEntity> optionalPayment = paymentRepository.findById(id);
        if (optionalPayment.isPresent()) {
            paymentRepository.delete(optionalPayment.get());
            return RestAPIResponse.builder()
                    .success(true)
                    .statusCode(204)
                    .message(SUCCESSFULLY_DELETED)
                    .build();
        }
        return RestAPIResponse.builder()
                .success(false)
                .statusCode(404)
                .message(PAYMENT + NOT_FOUND)
                .build();
    }

    @Override
    public RestAPIResponse get(Long id) {
        Optional<PaymentEntity> optionalPayment = paymentRepository.findById(id);
        if (optionalPayment.isPresent()) {
            PaymentResponseDto responseDto = modelMapper.map(optionalPayment.get(), PaymentResponseDto.class);
            return RestAPIResponse.builder()
                    .success(true)
                    .statusCode(200)
                    .message(PAYMENT)
                    .data(responseDto)
                    .build();
        }
        return RestAPIResponse.builder()
                .success(false)
                .message(PAYMENT + NOT_FOUND)
                .statusCode(404)
                .build();
    }

    @Override
    public RestAPIResponse getAll(Pageable pageable) {
        final Page<PaymentEntity> paymentEntities = paymentRepository.findAll(pageable);
        final List<PaymentResponseDto> list = paymentEntities.getContent().size() > 0 ?
                paymentEntities.getContent().stream().map(u -> modelMapper.map(u, PaymentResponseDto.class)).toList()
                : new ArrayList<>();
        PageImpl<PaymentResponseDto> responses = new PageImpl<>(list, paymentEntities.getPageable(), paymentEntities.getTotalPages());
        return RestAPIResponse.builder()
                .success(true)
                .statusCode(200)
                .data(responses)
                .message(DATA_LIST)
                .build();
    }
}
