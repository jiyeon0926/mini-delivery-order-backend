package mini.delivery.domain.user.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class NicknameRequestDto {

    @Size(min = 2, max = 10)
    @Pattern(
            regexp = "^[가-힣a-zA-Z0-9]+$",
            message = "한글, 영문, 숫자만 입력 가능합니다."
    )
    private final String nickname;
}
