package mini.delivery.domain.store.service;

import lombok.RequiredArgsConstructor;
import mini.delivery.domain.store.dto.StoreCreateRequestDto;
import mini.delivery.domain.store.dto.StoreCreateResponseDto;
import mini.delivery.domain.store.entity.Store;
import mini.delivery.domain.store.repository.StoreRepository;
import mini.delivery.domain.user.entity.User;
import mini.delivery.domain.user.repository.UserRepository;
import mini.delivery.global.error.CustomException;
import mini.delivery.global.error.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;
    private final UserRepository userRepository;

    @Transactional
    public StoreCreateResponseDto createStore(StoreCreateRequestDto storeCreateRequestDto, String email) {
        User user = userRepository.findByEmailAndIsDeletedFalse(email)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        validateStoreLimit(user.getId());

        Store store = Store.create(
                user,
                storeCreateRequestDto.getName(),
                storeCreateRequestDto.getAddress(),
                storeCreateRequestDto.getMinOrderAmount(),
                storeCreateRequestDto.getOpenTime(),
                storeCreateRequestDto.getCloseTime()
        );
        Store savedStore = storeRepository.save(store);

        return StoreCreateResponseDto.from(savedStore);
    }

    private void validateStoreLimit(Long userId) {
        long storeCount = storeRepository.countByUserId(userId);
        if (storeCount >= 3) {
            throw new CustomException(ErrorCode.STORE_LIMIT_EXCEEDED);
        }
    }
}
