package mini.delivery.domain.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class PasswordRequestDto {

    @NotBlank
    private final String oldPassword;

    @NotBlank
    @Size(min = 8)
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&]).*$",
            message = "비밀번호는 대문자, 소문자, 숫자, 특수문자를 포함해야 합니다."
    )
    private final String newPassword;
}
