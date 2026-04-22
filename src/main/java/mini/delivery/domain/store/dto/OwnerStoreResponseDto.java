package mini.delivery.domain.store.dto;

import lombok.Getter;
import mini.delivery.domain.store.entity.Store;

import java.time.LocalDateTime;

@Getter
public class OwnerStoreResponseDto {

    private final Long id;
    private final String name;
    private final double averageRating;
    private final Long reviewCount;
    private final String storeStatus;
    private final boolean deleted;
    private final LocalDateTime createdAt;

    private OwnerStoreResponseDto(Long id, String name, double averageRating, Long reviewCount, String storeStatus, boolean deleted, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.averageRating = averageRating;
        this.reviewCount = reviewCount;
        this.storeStatus = storeStatus;
        this.deleted = deleted;
        this.createdAt = createdAt;
    }

    public static OwnerStoreResponseDto from(Store store, double averageRating, Long reviewCount) {
        return new OwnerStoreResponseDto(
                store.getId(),
                store.getName(),
                averageRating,
                reviewCount,
                store.getStoreStatus().name(),
                store.isDeleted(),
                store.getCreatedAt()
        );
    }
}
