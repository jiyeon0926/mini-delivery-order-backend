package mini.delivery.domain.review.service;

import lombok.RequiredArgsConstructor;
import mini.delivery.domain.order.entity.Order;
import mini.delivery.domain.order.repository.OrderRepository;
import mini.delivery.domain.review.dto.ReviewCreateRequestDto;
import mini.delivery.domain.review.dto.ReviewCreateResponseDto;
import mini.delivery.domain.review.entity.Review;
import mini.delivery.domain.review.repository.ReviewRepository;
import mini.delivery.domain.store.repository.StoreRepository;
import mini.delivery.domain.user.entity.User;
import mini.delivery.domain.user.repository.UserRepository;
import mini.delivery.global.error.CustomException;
import mini.delivery.global.error.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final UserRepository userRepository;
    private final OrderRepository orderRepository;
    private final ReviewRepository reviewRepository;
    private final StoreRepository storeRepository;

    @Transactional
    public ReviewCreateResponseDto createReview(Long orderId, ReviewCreateRequestDto reviewCreateRequestDto, String email){
        User user = userRepository.findByEmailAndIsDeletedFalse(email)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new CustomException(ErrorCode.ORDER_NOT_FOUND));
        if (!order.getUser().getId().equals(user.getId())) {
                throw new CustomException(ErrorCode.ORDER_NOT_FOUND); 
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
    public void deleteReview(Long storeId, Long reviewId, String email){
        User user = userRepository.findByEmailAndIsDeletedFalse(email)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        storeRepository.findByIdAndUserId(storeId,user.getId())
                .orElseThrow(() -> new CustomException(ErrorCode.STORE_NOT_FOUND));
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new CustomException(ErrorCode.REVIEW_NOT_FOUND));
        if(!review.getOrder().getStore().getId().equals(storeId)){
            throw new CustomException(ErrorCode.REVIEW_NOT_FOUND);
        }
        review.deleteReview();
    }
}
