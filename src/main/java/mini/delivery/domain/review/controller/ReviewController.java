package mini.delivery.domain.review.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mini.delivery.domain.review.dto.ReviewCreateRequestDto;
import mini.delivery.domain.review.dto.ReviewCreateResponseDto;
import mini.delivery.domain.review.service.ReviewService;
import mini.delivery.global.auth.UserDetailsImpl;
import mini.delivery.global.common.dto.CommonResponseBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    // 리뷰 작성
    @PostMapping("/orders/{orderId}/review")
    public ResponseEntity<CommonResponseBody<ReviewCreateResponseDto>> createReview(@PathVariable Long orderId,
                                                                                   @Valid @RequestBody ReviewCreateRequestDto reviewCreateRequestDto,
                                                                                   @AuthenticationPrincipal UserDetailsImpl userDetails){
        ReviewCreateResponseDto reviewCreateResponseDto = reviewService.createReview(
                orderId,
                reviewCreateRequestDto,
                userDetails.getUsername()
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(CommonResponseBody.success("리뷰를 작성하였습니다.", reviewCreateResponseDto));
    }

    // 리뷰 삭제
    @DeleteMapping("/owner/stores/{storeId}/reviews/{reviewId}")
    public ResponseEntity<Void>deleteReview(@PathVariable Long storeId,
                                            @PathVariable Long reviewId,
                                            @AuthenticationPrincipal UserDetailsImpl userDetails){

        reviewService.deleteReview(
                storeId,
                reviewId,
                userDetails.getUsername()
        );

        return ResponseEntity.noContent().build();
    }

}
