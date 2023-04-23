package uz.service.user;


import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.dto.response.JWTResponse;
import uz.dto.request.AuthenticationRequestDto;
import uz.dto.request.RegisterRequestDto;
import uz.model.entity.user.UserEntity;
import uz.model.enums.Gender;
import uz.model.enums.Role;
import uz.repository.UserRepository;
import uz.service.jwt.JwtService;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public JWTResponse register(RegisterRequestDto request){
        var user = UserEntity.builder()
                .firstName(request.getFirstName())
                .password(passwordEncoder.encode(request.getPassword()))
                .phoneNumber(request.getPhoneNumber())
                .gender(Gender.valueOf(request.getGender()))
                .build();
        user.setRole(Role.STUDENT);
        userRepository.save(user);
        String token = jwtService.generateToken(user);
        return JWTResponse.builder()
                .token(token)
                .statusCode(201)
                .build();
    }

    public JWTResponse authenticate(AuthenticationRequestDto request){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getPhoneNumber(),
                        request.getPassword()
                )
        );
        var user = userRepository.findByPhoneNumber(request.getPhoneNumber())
                .orElseThrow();
        var token = jwtService.generateToken(user);
        return JWTResponse.builder()
                .token(token)
                .statusCode(200)
                .build();
    }

}
