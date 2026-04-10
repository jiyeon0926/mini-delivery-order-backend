package mini.delivery.domain.store.service;

import lombok.RequiredArgsConstructor;
import mini.delivery.domain.menu.entity.Menu;
import mini.delivery.domain.menu.repository.MenuRepository;
import mini.delivery.domain.review.repository.ReviewRepository;
import mini.delivery.domain.store.dto.*;
import mini.delivery.domain.store.entity.Store;
import mini.delivery.domain.store.repository.StoreRepository;
import mini.delivery.domain.user.entity.User;
import mini.delivery.domain.user.repository.UserRepository;
import mini.delivery.global.error.CustomException;
import mini.delivery.global.error.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StoreService {

    private final StoreRepository storeRepository;
    private final UserRepository userRepository;
    private final ReviewRepository reviewRepository;
    private final MenuRepository menuRepository;

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

    /**
     * TODO 가게 수정
     * 현재 가게 이름과 주소만 수정 가능
     */
    @Transactional
    public void updateStore(Long storeId, StoreUpdateRequestDto storeUpdateRequestDto, String email) {
        User user = userRepository.findByEmailAndIsDeletedFalse(email)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        Store store = storeRepository.findByIdAndUserId(storeId, user.getId())
                .orElseThrow(() -> new CustomException(ErrorCode.STORE_NOT_FOUND));

        store.updateName(storeUpdateRequestDto.getName());
        store.updateAddress(storeUpdateRequestDto.getAddress());
    }

    @Transactional(readOnly = true)
    public StoreResponseDto getStoreById(Long storeId) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new CustomException(ErrorCode.STORE_NOT_FOUND));

        float averageRating = reviewRepository.averageRatingByStoreId(storeId);
        long reviewCount = reviewRepository.countByStoreId(storeId);

        List<Menu> menus = menuRepository.findAllByStoreId(storeId);

        return StoreResponseDto.from(store, averageRating, reviewCount, StoreMenuResponseDto.from(menus));
    }

    private void validateStoreLimit(Long userId) {
        long storeCount = storeRepository.countByUserId(userId);
        if (storeCount >= 3) {
            throw new CustomException(ErrorCode.STORE_LIMIT_EXCEEDED);
        }
    }
}
