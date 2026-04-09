package mini.delivery.domain.user.service;

import lombok.RequiredArgsConstructor;
import mini.delivery.domain.user.dto.NicknameResponseDto;
import mini.delivery.domain.user.dto.UserResponseDto;
import mini.delivery.domain.user.entity.User;
import mini.delivery.domain.user.repository.UserRepository;
import mini.delivery.global.error.CustomException;
import mini.delivery.global.error.ErrorCode;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public NicknameResponseDto updateNickname(String nickname, String email) {
        User user = userRepository.findByEmailAndIsDeletedFalse(email)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        user.updateNickname(nickname);

        return NicknameResponseDto.from(user);
    }

    @Transactional
    public void updatePassword(String oldPassword, String newPassword, String email) {
        User user = userRepository.findByEmailAndIsDeletedFalse(email)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        validatePassword(oldPassword, user.getPassword());

        String encodedPassword = passwordEncoder.encode(newPassword);
        user.updatePassword(encodedPassword);
    }

    @Transactional
    public void deleteUser(String email) {
        User user = userRepository.findByEmailAndIsDeletedFalse(email)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        user.delete();
    }

    @Transactional(readOnly = true)
    public UserResponseDto getMyProfile(String email) {
        User user = userRepository.findByEmailAndIsDeletedFalse(email)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        return UserResponseDto.from(user);
    }

    private void validatePassword(String rawPassword, String encodedPassword) {
        boolean isNotValid = !passwordEncoder.matches(rawPassword, encodedPassword);

        if (isNotValid) {
            throw new CustomException(ErrorCode.PASSWORD_NOT_MATCH);
        }
    }
}
