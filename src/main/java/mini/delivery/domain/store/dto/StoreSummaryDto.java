package mini.delivery.domain.store.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import mini.delivery.global.common.enums.StoreStatus;

@Getter
public class StoreSummaryDto {

    private final Long id;
    private final String name;
    private final StoreStatus storeStatus;
    private final int minOrderAmount;
    private final double averageRating;
    private final long reviewCount;

    @QueryProjection
    public StoreSummaryDto(Long id, String name, StoreStatus storeStatus, int minOrderAmount, double averageRating, long reviewCount) {
        this.id = id;
        this.name = name;
        this.storeStatus = storeStatus;
        this.minOrderAmount = minOrderAmount;
        this.averageRating = averageRating;
        this.reviewCount = reviewCount;
    }
}
