package mini.delivery.domain.review.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class StoreReviewResponseDto {

    private final long total;
    private final List<ReviewListResponseDto> reviews;

    private StoreReviewResponseDto(long total, List<ReviewListResponseDto> reviews) {
        this.total = total;
        this.reviews = reviews;
    }

    public static StoreReviewResponseDto from(long total, List<ReviewListResponseDto> reviews) {
        return new StoreReviewResponseDto(total, reviews);
    }
}
