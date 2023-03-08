package uz.dto.request;

import lombok.*;

import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CourseRequestDto {

    @NotNull
    private String name;
    private String description;
    private double price;
    private int duration;
}
