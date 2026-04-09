package mini.delivery.domain.user.dto;

import lombok.Getter;
import mini.delivery.domain.user.entity.User;

@Getter
public class NicknameResponseDto {

    private final Long id;
    private final String nickname;

    private NicknameResponseDto(Long id, String nickname) {
        this.id = id;
        this.nickname = nickname;
    }

    public static NicknameResponseDto from(User user) {
        return new NicknameResponseDto(user.getId(), user.getNickname());
    }
}
