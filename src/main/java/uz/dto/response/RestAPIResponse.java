package uz.dto.response;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class RestAPIResponse {

    private String message;
    private int statusCode;
    private boolean success;
    private Object data;

    public RestAPIResponse(String message, int statusCode, boolean success) {
        this.message = message;
        this.statusCode = statusCode;
        this.success = success;
    }
}
