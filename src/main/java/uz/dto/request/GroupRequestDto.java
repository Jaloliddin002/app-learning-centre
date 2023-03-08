package uz.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Service
@Builder
public class GroupRequestDto {

    @NotNull
    private String name;
    @NotNull
    private Long courseId;
    @NotNull
    private Long teacherId;
    private String startTime;
    private String endTime;
    private String courseType;
    private int members;
}
