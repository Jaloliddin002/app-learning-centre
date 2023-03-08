package uz.dto.response;


import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class GroupResponseDto {

    private String name;
    private Long teacherId;
    private Long courseId;
    private String courseType;
    private String startTime;
    private String endTime;
    private List<Long> studentsId;
}
