package mini.delivery.domain.user.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mini.delivery.domain.user.dto.NicknameRequestDto;
import mini.delivery.domain.user.dto.NicknameResponseDto;
import mini.delivery.domain.user.service.UserService;
import mini.delivery.global.auth.UserDetailsImpl;
import mini.delivery.global.common.dto.CommonResponseBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users/me")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PatchMapping("/nickname")
    public ResponseEntity<CommonResponseBody<NicknameResponseDto>> updateNickname(@Valid @RequestBody NicknameRequestDto nicknameRequestDto,
                                                                                  @AuthenticationPrincipal UserDetailsImpl userDetails) {
        NicknameResponseDto nicknameResponseDto = userService.updateNickname(
                nicknameRequestDto.getNickname(),
                userDetails.getUsername()
        );

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(CommonResponseBody.success("닉네임을 변경하였습니다.", nicknameResponseDto));
    }
}
