package mini.delivery.domain.review.service;

import lombok.RequiredArgsConstructor;
import mini.delivery.domain.order.entity.Order;
import mini.delivery.domain.order.repository.OrderRepository;
import mini.delivery.domain.review.dto.*;
import mini.delivery.domain.review.entity.Review;
import mini.delivery.domain.review.repository.ReviewRepository;
import mini.delivery.domain.store.repository.StoreRepository;
import mini.delivery.domain.user.entity.User;
import mini.delivery.domain.user.repository.UserRepository;
import mini.delivery.global.common.enums.OrderStatus;
import mini.delivery.global.error.CustomException;
import mini.delivery.global.error.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final ReviewRepository reviewRepository;
    private final StoreRepository storeRepository;

    @Transactional
    public ReviewCreateResponseDto createReview(Long orderId, ReviewCreateRequestDto reviewCreateRequestDto, String email) {
        User user = userRepository.findByEmailAndIsDeletedFalse(email)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        Order order = orderRepository.findByIdAndUserId(orderId, user.getId())
                .orElseThrow(() -> new CustomException(ErrorCode.ORDER_NOT_FOUND));
        if (order.getOrderStatus() != OrderStatus.DELIVERED) {
            throw new CustomException(ErrorCode.ORDER_NOT_DELIVERED);
        }
        if (reviewRepository.existsByOrderId(orderId)) {
            throw new CustomException(ErrorCode.REVIEW_ALREADY_EXISTS);
        }

        Review review = Review.create(
                user,
                order,
                reviewCreateRequestDto.getRating(),
                reviewCreateRequestDto.getContent()
        );
        Review savedReview = reviewRepository.save(review);
        return ReviewCreateResponseDto.from(savedReview);
    }

    @Transactional
    public void deleteReview(Long storeId, Long reviewId, String email) {
        User user = userRepository.findByEmailAndIsDeletedFalse(email)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        storeRepository.findByIdAndUserId(storeId, user.getId())
                .orElseThrow(() -> new CustomException(ErrorCode.STORE_NOT_FOUND));
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new CustomException(ErrorCode.REVIEW_NOT_FOUND));
        if (!review.getOrder().getStore().getId().equals(storeId)) {
            throw new CustomException(ErrorCode.REVIEW_NOT_FOUND);
        }
        review.deleteReview();
    }

    @Transactional(readOnly = true)
    public StoreReviewResponseDto allFindReview(Long storeId) {
        List<Review> reviews = reviewRepository.findAllByStoreId(storeId);
        long total = reviewRepository.countByStoreId(storeId);

        return StoreReviewResponseDto.from(total, ReviewListResponseDto.from(reviews));
    }

    @Transactional(readOnly = true)
    public List<MyReviewResponseDto> findUserReview(String email) {
        User user = userRepository.findByEmailAndIsDeletedFalse(email)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        List<Review> reviews = reviewRepository.findAllMyReviews(user.getId());

        return reviews.stream()
                .map(MyReviewResponseDto::from)
                .toList();
    }
}
