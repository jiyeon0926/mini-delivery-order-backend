package mini.delivery.domain.user.service;

import lombok.RequiredArgsConstructor;
import mini.delivery.domain.user.dto.NicknameResponseDto;
import mini.delivery.domain.user.dto.UserResponseDto;
import mini.delivery.domain.user.entity.User;
import mini.delivery.domain.user.repository.UserRepository;
import mini.delivery.global.error.CustomException;
import mini.delivery.global.error.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public NicknameResponseDto updateNickname(String nickname, String email) {
        User user = userRepository.findByEmailAndIsDeletedFalse(email)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        user.updateNickname(nickname);

        return NicknameResponseDto.from(user);
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
}
