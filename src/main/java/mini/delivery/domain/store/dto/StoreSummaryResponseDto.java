package mini.delivery.domain.store.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class StoreSummaryResponseDto {

    private final Long id;
    private final String name;
    private final String storeStatus;
    private final int minOrderAmount;
    private final double averageRating;
    private final long reviewCount;

    private StoreSummaryResponseDto(Long id, String name, String storeStatus, int minOrderAmount, double averageRating, long reviewCount) {
        this.id = id;
        this.name = name;
        this.storeStatus = storeStatus;
        this.minOrderAmount = minOrderAmount;
        this.averageRating = averageRating;
        this.reviewCount = reviewCount;
    }

    public static StoreSummaryResponseDto from(StoreSummaryDto storeSummaryDto) {
        return new StoreSummaryResponseDto(
                storeSummaryDto.getId(),
                storeSummaryDto.getName(),
                storeSummaryDto.getStoreStatus().name(),
                storeSummaryDto.getMinOrderAmount(),
                storeSummaryDto.getAverageRating(),
                storeSummaryDto.getReviewCount()
        );
    }

    public static List<StoreSummaryResponseDto> from(List<StoreSummaryDto> storeSummaryDtoList) {
        return storeSummaryDtoList.stream()
                .map(StoreSummaryResponseDto::from)
                .toList();
    }
}
