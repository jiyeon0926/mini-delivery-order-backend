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
import mini.delivery.global.common.enums.StoreStatus;
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

    @Transactional
    public void updateStore(Long storeId, StoreUpdateRequestDto storeUpdateRequestDto, String email) {
        User user = userRepository.findByEmailAndIsDeletedFalse(email)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        Store store = storeRepository.findByIdAndUserIdAndIsDeletedFalse(storeId, user.getId())
                .orElseThrow(() -> new CustomException(ErrorCode.STORE_NOT_FOUND));

        if (storeUpdateRequestDto.getName() != null) {
            store.updateName(storeUpdateRequestDto.getName());
        }
        if (storeUpdateRequestDto.getAddress() != null) {
            store.updateAddress(storeUpdateRequestDto.getAddress());
        }
        if (storeUpdateRequestDto.getMinOrderAmount() != null) {
            store.updateMinOrderAmount(storeUpdateRequestDto.getMinOrderAmount());
        }
        if (storeUpdateRequestDto.getOpenTime() != null) {
            store.updateOpenTime(storeUpdateRequestDto.getOpenTime());
        }
        if (storeUpdateRequestDto.getCloseTime() != null) {
            store.updateCloseTime(storeUpdateRequestDto.getCloseTime());
        }
    }

    @Transactional(readOnly = true)
    public StoreResponseDto getStoreById(Long storeId) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new CustomException(ErrorCode.STORE_NOT_FOUND));

        double averageRating = reviewRepository.averageRatingByStoreId(storeId);
        long reviewCount = reviewRepository.countByStoreId(storeId);

        List<Menu> menus = menuRepository.findAllByStoreId(storeId);

        return StoreResponseDto.from(store, averageRating, reviewCount, StoreMenuResponseDto.from(menus));
    }

    @Transactional(readOnly = true)
    public List<StoreSummaryResponseDto> searchStores(String keyword) {
        List<StoreSummaryDto> stores = storeRepository.searchStores(keyword);

        return StoreSummaryResponseDto.from(stores);
    }

    @Transactional
    public StoreStatusChangeResponseDto storeStatus(Long storeId, String storeStatus, String email) {
        User user = userRepository.findByEmailAndIsDeletedFalse(email)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        Store store = storeRepository.findByIdAndUserIdAndIsDeletedFalse(storeId, user.getId())
                .orElseThrow(() -> new CustomException(ErrorCode.STORE_NOT_FOUND));

        store.updateStoreStatus(StoreStatus.of(storeStatus));

        return new StoreStatusChangeResponseDto(store.getId(), store.getStoreStatus().name());
    }

    @Transactional
    public void closeStore(Long storeId, String email) {
        User user = userRepository.findByEmailAndIsDeletedFalse(email)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        Store store = storeRepository.findByIdAndUserIdAndIsDeletedFalse(storeId, user.getId())
                .orElseThrow(() -> new CustomException(ErrorCode.STORE_NOT_FOUND));

        if (store.isOpenStatus()) {
            throw new CustomException(ErrorCode.STORE_MUST_BE_CLOSED_FIRST);
        }

        store.deleteStore();
    }

    @Transactional(readOnly = true)
    public List<OwnerStoreResponseDto> allFindStore(String email) {
        User user = userRepository.findByEmailAndIsDeletedFalse(email)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        List<Store> stores = storeRepository.findAllByUserId(user.getId());

        return stores.stream()
                .map(store -> {
                    double averageRating = reviewRepository.averageRatingByStoreId(store.getId());
                    long reviewCount = reviewRepository.countByStoreId(store.getId());

                    return OwnerStoreResponseDto.from(store, averageRating, reviewCount);
                })
                .toList();
    }

    private void validateStoreLimit(Long userId) {
        long storeCount = storeRepository.countByUserId(userId);
        if (storeCount >= 3) {
            throw new CustomException(ErrorCode.STORE_LIMIT_EXCEEDED);
        }
    }


}
