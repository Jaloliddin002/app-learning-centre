package uz.dto.request;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class QueueRequestDto {

    private Long userId;
    private Long courseId;
    private String status;
}
