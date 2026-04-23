package mini.delivery.domain.review.dto;

import lombok.Getter;
import mini.delivery.domain.review.entity.Review;

import java.time.LocalDateTime;

@Getter
public class MyReviewResponseDto {

    private final Long reviewId;
    private final Long storeId;
    private final String storeName;
    private final String nickName;
    private final int rating;
    private final String content;
    private final LocalDateTime createdAt;

    private MyReviewResponseDto(Long reviewId, Long storeId, String storeName, String nickName, int rating, String content, LocalDateTime createdAt) {
        this.reviewId = reviewId;
        this.storeId = storeId;
        this.storeName = storeName;
        this.nickName = nickName;
        this.rating = rating;
        this.content = content;
        this.createdAt = createdAt;
    }

    public static MyReviewResponseDto from(Review review){
        return new MyReviewResponseDto(
                review.getId(),
                review.getOrder().getStore().getId(),
                review.getOrder().getStore().getName(),
                review.getUser().getNickname(),
                review.getRating(),
                review.getContent(),
                review.getCreatedAt()
        );
    }
}
