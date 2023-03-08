package uz.dto.request;


import lombok.*;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class AuthenticationRequestDto {

    @NotNull
    private String phoneNumber;
    @NotNull
    private String password;
}
