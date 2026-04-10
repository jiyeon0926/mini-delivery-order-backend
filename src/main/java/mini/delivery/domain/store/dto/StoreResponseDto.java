package mini.delivery.domain.store.dto;

import lombok.Getter;
import mini.delivery.domain.store.entity.Store;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Getter
public class StoreResponseDto {

    private final Long id;
    private final String storeName;
    private final String address;
    private final int minOrderAmount;
    private final LocalTime openTime;
    private final LocalTime closeTime;
    private final String storeStatus;
    private final float averageRating;
    private final long reviewCount;
    private final LocalDateTime createdAt;
    private final List<StoreMenuResponseDto> menus;

    private StoreResponseDto(Long id, String storeName, String address, int minOrderAmount, LocalTime openTime, LocalTime closeTime, String storeStatus, float averageRating, long reviewCount, LocalDateTime createdAt, List<StoreMenuResponseDto> menus) {
        this.id = id;
        this.storeName = storeName;
        this.address = address;
        this.minOrderAmount = minOrderAmount;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.storeStatus = storeStatus;
        this.averageRating = averageRating;
        this.reviewCount = reviewCount;
        this.createdAt = createdAt;
        this.menus = menus;
    }

    public static StoreResponseDto from(Store store, float averageRating, long reviewCount, List<StoreMenuResponseDto> menus) {
        return new StoreResponseDto(
                store.getId(),
                store.getName(),
                store.getAddress(),
                store.getMinOrderAmount(),
                store.getOpenTime(),
                store.getCloseTime(),
                store.getStoreStatus().name(),
                averageRating,
                reviewCount,
                store.getCreatedAt(),
                menus
        );
    }
}
