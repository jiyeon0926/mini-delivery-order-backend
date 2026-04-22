package mini.delivery.domain.auth.service;

import lombok.RequiredArgsConstructor;
import mini.delivery.domain.auth.dto.AuthTokenResponseDto;
import mini.delivery.domain.auth.dto.SignupResponseDto;
import mini.delivery.domain.user.entity.User;
import mini.delivery.domain.user.repository.UserRepository;
import mini.delivery.global.auth.AuthenticationScheme;
import mini.delivery.global.auth.RefreshTokenService;
import mini.delivery.global.auth.jwt.JwtProvider;
import mini.delivery.global.error.CustomException;
import mini.delivery.global.error.ErrorCode;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final RefreshTokenService refreshTokenService;

    @Transactional
    public SignupResponseDto signupCustomer(String email, String password, String nickname) {
        validateDuplicateEmail(email);

        String encodedPassword = passwordEncoder.encode(password);
        User user = User.ofCustomer(email, encodedPassword, nickname);
        User savedUser = userRepository.save(user);

        return SignupResponseDto.from(savedUser);
    }

    @Transactional
    public SignupResponseDto signupOwner(String email, String password, String nickname) {
        validateDuplicateEmail(email);

        String encodedPassword = passwordEncoder.encode(password);
        User user = User.ofOwner(email, encodedPassword, nickname);
        User savedUser = userRepository.save(user);

        return SignupResponseDto.from(savedUser);
    }

    public AuthTokenResponseDto login(String email, String password) {
        User user = userRepository.findByEmailAndIsDeletedFalse(email)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        validatePassword(password, user.getPassword());

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(email, password));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = jwtProvider.generateAccessToken(authentication);
        String refreshToken = jwtProvider.generateRefreshToken(authentication);
        refreshTokenService.saveRefreshToken(refreshToken, email, jwtProvider.getRefreshExpiryMillis());

        return AuthTokenResponseDto.of(AuthenticationScheme.BEARER.getName(), accessToken, refreshToken, user.getRole().name());
    }

    public void logout(String email) {
        refreshTokenService.deleteRefreshToken(email);
    }

    private void validateDuplicateEmail(String email) {
        boolean exists = userRepository.existsByEmail(email);
        if (exists) {
            throw new CustomException(ErrorCode.EMAIL_ALREADY_EXISTS);
        }
    }

    private void validatePassword(String rawPassword, String encodedPassword) {
        boolean isNotValid = !passwordEncoder.matches(rawPassword, encodedPassword);

        if (isNotValid) {
            throw new CustomException(ErrorCode.PASSWORD_NOT_MATCH);
        }
    }
}
