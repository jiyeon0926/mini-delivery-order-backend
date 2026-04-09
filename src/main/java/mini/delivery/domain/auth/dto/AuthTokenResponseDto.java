package mini.delivery.domain.auth.dto;

import lombok.Getter;

@Getter
public class AuthTokenResponseDto {

    private final String tokenAuthScheme;
    private final String accessToken;
    private final String refreshToken;

    private AuthTokenResponseDto(String tokenAuthScheme, String accessToken, String refreshToken) {
        this.tokenAuthScheme = tokenAuthScheme;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public static AuthTokenResponseDto of(String tokenAuthScheme, String accessToken, String refreshToken) {
        return new AuthTokenResponseDto(tokenAuthScheme, accessToken, refreshToken);
    }
}
