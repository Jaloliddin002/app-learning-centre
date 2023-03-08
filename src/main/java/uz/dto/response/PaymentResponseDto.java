package uz.dto.response;


import lombok.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class PaymentResponseDto {

    private Long id;
    private Long userId;
    private LocalDateTime appliedDate;
    private double amount;


}
