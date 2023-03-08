package uz.dto.request;


import lombok.*;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class RegisterRequestDto {

    private String firstName;
    @NotNull
    private String phoneNumber;
    @NotNull
    private String password;
    private String gender;
}
