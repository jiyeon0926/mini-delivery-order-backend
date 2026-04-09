package mini.delivery.domain.user.dto;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class NicknameRequestDto {

    @Size(min = 2, max = 10)
    private final String nickname;
}
