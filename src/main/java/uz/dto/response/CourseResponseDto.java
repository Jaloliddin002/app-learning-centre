package uz.dto.response;


import lombok.*;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CourseResponseDto {

    private Long id;
    private String name;
    private String description;
    private int duration;
    private double price;
    private List<Long> teachersId;
}
