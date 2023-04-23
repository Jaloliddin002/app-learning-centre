package uz.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.dto.request.CourseRequestDto;
import uz.dto.response.RestAPIResponse;
import uz.service.course.CourseService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/courses")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @PostMapping
    public ResponseEntity<RestAPIResponse> create(
            @RequestBody @Valid CourseRequestDto requestDto
    ){
        RestAPIResponse apiResponse = courseService.create(requestDto);
        return ResponseEntity.status(apiResponse.isSuccess()
                ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST).body(apiResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestAPIResponse> get(
            @PathVariable Long id
    ){
        RestAPIResponse apiResponse = courseService.get(id);
        return ResponseEntity.status(apiResponse.isSuccess()
                ? HttpStatus.OK : HttpStatus.NOT_FOUND).body(apiResponse);
    }

    @GetMapping
    public ResponseEntity<RestAPIResponse> getAll(
            Pageable pageable
    ){
        RestAPIResponse apiResponse = courseService.getAll(pageable);
        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RestAPIResponse> update(
            @PathVariable Long id,
            @RequestBody CourseRequestDto courseRequestDto
    ){
        RestAPIResponse apiResponse = courseService.update(id, courseRequestDto);
        return ResponseEntity.status(apiResponse.isSuccess()
                ? HttpStatus.ACCEPTED : HttpStatus.NOT_FOUND).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RestAPIResponse> delete(
            @PathVariable Long id
    ){
        RestAPIResponse apiResponse = courseService.delete(id);
        return ResponseEntity.status(apiResponse.isSuccess()
                ? HttpStatus.NO_CONTENT : HttpStatus.NOT_FOUND).body(apiResponse);
    }
}
