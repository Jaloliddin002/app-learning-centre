package uz.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.dto.request.GroupRequestDto;
import uz.dto.response.RestAPIResponse;
import uz.service.group.GroupService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/groups")
@RequiredArgsConstructor
public class GroupController {

    private final GroupService groupService;

    @PostMapping
    public ResponseEntity<RestAPIResponse> create(
            @RequestBody @Valid GroupRequestDto requestDto
    ){
        RestAPIResponse apiResponse = groupService.create(requestDto);
        return ResponseEntity.status(apiResponse.isSuccess()
                ? HttpStatus.CREATED : HttpStatus.NOT_FOUND).body(apiResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestAPIResponse> get(
            @PathVariable Long id
    ){
        RestAPIResponse apiResponse = groupService.get(id);
        return ResponseEntity.status(apiResponse.isSuccess()
                ? HttpStatus.OK : HttpStatus.NOT_FOUND).body(apiResponse);
    }

    @GetMapping
    public ResponseEntity<RestAPIResponse> getAll(
            Pageable pageable
    ){
        RestAPIResponse apiResponse = groupService.getAll(pageable);
        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RestAPIResponse> update(
            @PathVariable Long id,
            @RequestBody GroupRequestDto requestDto
    ){
        RestAPIResponse apiResponse = groupService.update(id, requestDto);
        return ResponseEntity.status(apiResponse.isSuccess()
                ? HttpStatus.ACCEPTED : HttpStatus.NOT_FOUND).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RestAPIResponse> delete(
            @PathVariable Long id
    ){
        RestAPIResponse apiResponse = groupService.delete(id);
        return ResponseEntity.status(apiResponse.isSuccess()
                ? HttpStatus.NO_CONTENT : HttpStatus.BAD_REQUEST).body(apiResponse);
    }

}
