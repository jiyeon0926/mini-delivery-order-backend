package mini.delivery.domain.user.dto;

import lombok.Getter;
import mini.delivery.domain.user.entity.User;

@Getter
public class UserResponseDto {

    private final Long id;
    private final String email;
    private final String nickname;
    private final String role;

    private UserResponseDto(Long id, String email, String nickname, String role) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.role = role;
    }

    public static UserResponseDto from(User user) {
        return new UserResponseDto(
                user.getId(),
                user.getEmail(),
                user.getNickname(),
                user.getRole().name()
        );
    }
}
