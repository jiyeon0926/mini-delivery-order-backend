package mini.delivery.global.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final StringRedisTemplate redisTemplate;

    public void saveRefreshToken(String refreshToken, String email, long refreshExpiryMillis) {
        redisTemplate.opsForValue().set(
                "refreshToken:" + email,
                refreshToken,
                refreshExpiryMillis,
                TimeUnit.MILLISECONDS
        );
    }
}
