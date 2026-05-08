package mini.delivery.domain.auth.dto;

import lombok.Getter;

@Getter
public class AuthTokenResponseDto {

    private final String tokenAuthScheme;
    private final String accessToken;
    private final String refreshToken;
    private final String role;

    private AuthTokenResponseDto(String tokenAuthScheme, String accessToken, String refreshToken, String role) {
        this.tokenAuthScheme = tokenAuthScheme;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.role = role;
    }

    public static AuthTokenResponseDto of(String tokenAuthScheme, String accessToken, String refreshToken, String role) {
        return new AuthTokenResponseDto(tokenAuthScheme, accessToken, refreshToken, role);
    }
}
