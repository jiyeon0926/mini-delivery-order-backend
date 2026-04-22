package mini.delivery.domain.review.dto;

import lombok.Getter;
import mini.delivery.domain.review.entity.Review;

import java.time.LocalDateTime;

@Getter
public class ReviewOwnerUserResponseDto {

    private final Long reviewid;
    private final Long userId;
    private final String nickname;
    private final int rating;
    private final String content;
    private final LocalDateTime createdAt;
    private final boolean deleted;

    private ReviewOwnerUserResponseDto(Long reviewid, Long userId ,String nickname, int rating, String content, LocalDateTime createdAt, boolean deleted) {
        this.reviewid = reviewid;
        this.userId = userId;
        this.nickname = nickname;
        this.content = content;
        this.createdAt = createdAt;
        this.rating = rating;
        this.deleted = deleted;
    }

    public static ReviewOwnerUserResponseDto from(Review review){
        return new ReviewOwnerUserResponseDto(
                review.getId(),
                review.getUser().getId(),
                review.getUser().getNickname(),
                review.getRating(),
                review.getContent(),
                review.getCreatedAt(),
                review.isDeleted()
        );
    }

}
