package uz.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.dto.response.JWTResponse;
import uz.dto.request.AuthenticationRequestDto;
import uz.dto.request.RegisterRequestDto;
import uz.service.user.AuthService;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<JWTResponse> register(
            @RequestBody RegisterRequestDto request
    ){
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<JWTResponse> authenticate(
           @RequestBody AuthenticationRequestDto request
    ){
        return ResponseEntity.ok(authService.authenticate(request));
    }


}
