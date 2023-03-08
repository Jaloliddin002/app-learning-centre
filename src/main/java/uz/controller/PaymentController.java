package uz.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.dto.request.PaymentRequestDto;
import uz.dto.response.RestAPIResponse;
import uz.service.payment.PaymentService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<RestAPIResponse> create(
            @RequestBody @Valid PaymentRequestDto requestDto
    ){
        RestAPIResponse apiResponse = paymentService.create(requestDto);
        return ResponseEntity.status(apiResponse.isSuccess()
                ? HttpStatus.CREATED : HttpStatus.NOT_FOUND).body(apiResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestAPIResponse> get(
            @PathVariable Long id
    ){
        RestAPIResponse apiResponse = paymentService.get(id);
        return ResponseEntity.status(apiResponse.isSuccess()
                ? HttpStatus.OK : HttpStatus.NOT_FOUND).body(apiResponse);
    }

    @GetMapping
    public ResponseEntity<RestAPIResponse> getAll(
            Pageable pageable
    ) {
        RestAPIResponse apiResponse = paymentService.getAll(pageable);
        return ResponseEntity.ok(apiResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RestAPIResponse> update(
        @PathVariable Long id,
        @RequestBody PaymentRequestDto requestDto
    ){
        RestAPIResponse apiResponse = paymentService.update(id, requestDto);
        return ResponseEntity.status(apiResponse.isSuccess()
                ? HttpStatus.ACCEPTED : HttpStatus.NOT_FOUND).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RestAPIResponse> delete(
            @PathVariable Long id
    ){
        RestAPIResponse apiResponse = paymentService.delete(id);
        return ResponseEntity.status(apiResponse.isSuccess()
                ? HttpStatus.NO_CONTENT : HttpStatus.NOT_FOUND).body(apiResponse);
    }

}
