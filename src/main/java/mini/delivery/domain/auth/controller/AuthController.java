package mini.delivery.domain.auth.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mini.delivery.domain.auth.dto.SignupRequestDto;
import mini.delivery.domain.auth.dto.SignupResponseDto;
import mini.delivery.domain.auth.service.AuthService;
import mini.delivery.global.common.dto.CommonResponseBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/users")
    public ResponseEntity<CommonResponseBody<SignupResponseDto>> signupCustomer(@Valid @RequestBody SignupRequestDto signupRequestDto) {
        SignupResponseDto signupResponseDto = authService.signupCustomer(
                signupRequestDto.getEmail(),
                signupRequestDto.getPassword(),
                signupRequestDto.getNickname()
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(CommonResponseBody.success("회원가입을 성공했습니다.", signupResponseDto));
    }

    @PostMapping("/owners")
    public ResponseEntity<CommonResponseBody<SignupResponseDto>> signupOwner(@Valid @RequestBody SignupRequestDto signupRequestDto) {
        SignupResponseDto signupResponseDto = authService.signupOwner(
                signupRequestDto.getEmail(),
                signupRequestDto.getPassword(),
                signupRequestDto.getNickname()
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(CommonResponseBody.success("회원가입을 성공했습니다.", signupResponseDto));
    }
}
