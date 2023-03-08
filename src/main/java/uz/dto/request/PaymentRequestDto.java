package uz.dto.request;


import lombok.*;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class PaymentRequestDto {

    @NotNull
    private Long userId;
    private double amount;
}
