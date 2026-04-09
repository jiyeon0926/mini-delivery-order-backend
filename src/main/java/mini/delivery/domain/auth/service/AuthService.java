package mini.delivery.domain.auth.service;

import lombok.RequiredArgsConstructor;
import mini.delivery.domain.auth.dto.SignupResponseDto;
import mini.delivery.domain.user.entity.User;
import mini.delivery.domain.user.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public SignupResponseDto signupCustomer(String email, String password, String nickname) {
        String encodedPassword = passwordEncoder.encode(password);
        User user = User.ofCustomer(email, encodedPassword, nickname);
        User savedUser = userRepository.save(user);

        return SignupResponseDto.from(savedUser);
    }

    @Transactional
    public SignupResponseDto signupOwner(String email, String password, String nickname) {
        String encodedPassword = passwordEncoder.encode(password);
        User user = User.ofOwner(email, encodedPassword, nickname);
        User savedUser = userRepository.save(user);

        return SignupResponseDto.from(savedUser);
    }
}
