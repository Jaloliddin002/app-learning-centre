package uz.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.dto.request.RegisterRequestDto;
import uz.dto.response.RestAPIResponse;
import uz.service.user.UserService;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<RestAPIResponse> get(
            @PathVariable Long id
    ){
      return  ResponseEntity.ok(userService.get(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RestAPIResponse> update(
            @PathVariable Long id,
            @RequestBody @Valid RegisterRequestDto request
    ){
        return ResponseEntity.ok(userService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RestAPIResponse> delete (
            @PathVariable Long id
    ){
        return ResponseEntity.ok(userService.delete(id));
    }

    @GetMapping()
    public ResponseEntity<RestAPIResponse> getAll (Pageable pageable) {
        return ResponseEntity.ok(userService.getAll(pageable));
    }

}
