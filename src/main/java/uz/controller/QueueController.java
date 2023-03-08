package uz.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.dto.request.QueueRequestDto;
import uz.dto.response.RestAPIResponse;
import uz.service.queue.QueueService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/queues")
@RequiredArgsConstructor
public class QueueController {

    private final QueueService queueService;

    @PostMapping
    public ResponseEntity<RestAPIResponse> create(
            @RequestBody @Valid QueueRequestDto requestDto
    ){
        RestAPIResponse apiResponse = queueService.create(requestDto);
        return ResponseEntity.status(apiResponse.isSuccess()
                ? HttpStatus.CREATED : HttpStatus.NOT_FOUND).body(apiResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestAPIResponse> get(
            @PathVariable Long id
    ){
        RestAPIResponse apiResponse = queueService.get(id);
        return ResponseEntity.status(apiResponse.isSuccess()
                ? HttpStatus.OK : HttpStatus.NOT_FOUND).body(apiResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RestAPIResponse> update(
            @PathVariable Long id,
            @RequestBody QueueRequestDto requestDto
    ){
        RestAPIResponse apiResponse = queueService.update(id, requestDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.NO_CONTENT : HttpStatus.NOT_FOUND).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RestAPIResponse> delete(
            @PathVariable Long id
    ){
        RestAPIResponse apiResponse = queueService.delete(id);
        return ResponseEntity.status(apiResponse.isSuccess()
                ? HttpStatus.NO_CONTENT : HttpStatus.NOT_FOUND).body(apiResponse);
    }


    @GetMapping
    public ResponseEntity<RestAPIResponse> getAll(
            Pageable pageable
    ) {
        RestAPIResponse apiResponse = queueService.getAll(pageable);
        return ResponseEntity.ok(apiResponse);
    }

}
