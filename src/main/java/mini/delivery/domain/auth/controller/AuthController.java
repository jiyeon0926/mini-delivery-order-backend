package mini.delivery.domain.auth.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mini.delivery.domain.auth.dto.AuthTokenResponseDto;
import mini.delivery.domain.auth.dto.LoginRequestDto;
import mini.delivery.domain.auth.dto.SignupRequestDto;
import mini.delivery.domain.auth.dto.SignupResponseDto;
import mini.delivery.domain.auth.service.AuthService;
import mini.delivery.global.auth.UserDetailsImpl;
import mini.delivery.global.common.dto.CommonResponseBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    @PostMapping("/login")
    public ResponseEntity<CommonResponseBody<AuthTokenResponseDto>> login(@RequestBody LoginRequestDto loginRequestDto) {
        AuthTokenResponseDto authTokenResponseDto = authService.login(
                loginRequestDto.getEmail(),
                loginRequestDto.getPassword()
        );

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(CommonResponseBody.success("로그인을 성공했습니다.", authTokenResponseDto));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        authService.logout(userDetails.getUsername());

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
