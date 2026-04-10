package mini.delivery.domain.store.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;

@Getter
public class StoreSummaryDto {

    private final Long id;
    private final String name;
    private final int minOrderAmount;
    private final double averageRating;
    private final long reviewCount;

    @QueryProjection
    public StoreSummaryDto(Long id, String name, int minOrderAmount, double averageRating, long reviewCount) {
        this.id = id;
        this.name = name;
        this.minOrderAmount = minOrderAmount;
        this.averageRating = averageRating;
        this.reviewCount = reviewCount;
    }
}
