package mini.delivery.domain.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class SignupRequestDto {

    @NotBlank
    @Email
    @Size(max = 50)
    private final String email;

    @NotBlank
    @Size(min = 8)
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&]).*$",
            message = "비밀번호는 대문자, 소문자, 숫자, 특수문자를 포함해야 합니다."
    )
    private final String password;

    @NotBlank
    @Size(min = 2, max = 10)
    @Pattern(
            regexp = "^[가-힣a-zA-Z0-9]+$",
            message = "한글, 영문, 숫자만 입력 가능합니다."
    )
    private final String nickname;
}
