package uz.dto.response;

import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class QueueResponse {

    private Long id;
    private Long userId;
    private Long courseId;
    private LocalDate startDate;
    private String status;
}
