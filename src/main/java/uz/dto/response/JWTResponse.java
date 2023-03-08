package uz.dto.response;


import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class JWTResponse {

    private int statusCode;
    private String message;
    private String token;
}
