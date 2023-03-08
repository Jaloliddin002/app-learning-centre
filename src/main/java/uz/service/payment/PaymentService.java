package uz.service.payment;

import org.springframework.data.domain.Pageable;
import uz.dto.request.PaymentRequestDto;
import uz.service.BaseService;

public interface PaymentService extends BaseService<PaymentRequestDto, Pageable, Long> {

}
