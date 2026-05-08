package mini.delivery.domain.auth.dto;

import lombok.Getter;
import mini.delivery.domain.user.entity.User;

@Getter
public class SignupResponseDto {

    private final Long id;
    private final String role;

    private SignupResponseDto(Long id, String role) {
        this.id = id;
        this.role = role;
    }

    public static SignupResponseDto from(User user) {
        return new SignupResponseDto(user.getId(), user.getRole().name());
    }
}