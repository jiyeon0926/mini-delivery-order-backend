package mini.delivery.domain.review.dto;

import lombok.Getter;
import mini.delivery.domain.review.entity.Review;

@Getter
public class ReviewCreateResponseDto {

    private final Long id;

    private ReviewCreateResponseDto(Long id) {
        this.id = id;
    }

    public static ReviewCreateResponseDto from(Review review){
        return new ReviewCreateResponseDto(review.getId());
    }
}
