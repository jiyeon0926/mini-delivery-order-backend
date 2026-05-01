package mini.delivery.domain.review.dto;

import lombok.Getter;
import mini.delivery.domain.review.entity.Review;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class ReviewListResponseDto {

    private final Long reviewId;
    private final Long userId;
    private final String nickname;
    private final int rating;
    private final String content;
    private final LocalDateTime createdAt;
    private final boolean deleted;

    private ReviewListResponseDto(Long reviewId, Long userId, String nickname, int rating, String content, LocalDateTime createdAt, boolean deleted) {
        this.reviewId = reviewId;
        this.userId = userId;
        this.nickname = nickname;
        this.content = content;
        this.createdAt = createdAt;
        this.rating = rating;
        this.deleted = deleted;
    }

    public static ReviewListResponseDto from(Review review) {
        return new ReviewListResponseDto(
                review.getId(),
                review.getUser().getId(),
                review.getUser().getNickname(),
                review.getRating(),
                review.getContent(),
                review.getCreatedAt(),
                review.isDeleted()
        );
    }

    public static List<ReviewListResponseDto> from(List<Review> reviews) {
        return reviews.stream()
                .map(ReviewListResponseDto::from)
                .toList();
    }
}
