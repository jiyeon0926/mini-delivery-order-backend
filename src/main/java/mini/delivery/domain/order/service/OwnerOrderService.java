package mini.delivery.domain.order.service;

import lombok.RequiredArgsConstructor;
import mini.delivery.domain.order.dto.OrderStatusUpdateResponseDto;
import mini.delivery.domain.order.entity.Order;
import mini.delivery.domain.order.repository.OrderRepository;
import mini.delivery.domain.user.entity.User;
import mini.delivery.domain.user.repository.UserRepository;
import mini.delivery.global.common.enums.OrderStatus;
import mini.delivery.global.error.CustomException;
import mini.delivery.global.error.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OwnerOrderService {

    private final OrderRepository orderRepository;
    private final UserRepository userRepository;

    @Transactional
    public OrderStatusUpdateResponseDto updateOrderStatus(Long storeId, Long orderId, String orderStatus, String email) {
        User user = userRepository.findByEmailAndIsDeletedFalse(email)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        Order order = orderRepository.findByIdAndStoreIdAndOwnerId(orderId, storeId, user.getId())
                .orElseThrow(() -> new CustomException(ErrorCode.ORDER_NOT_FOUND));

        OrderStatus targetStatus = OrderStatus.of(orderStatus);
        validateOrderStatus(order, targetStatus);
        order.updateOrderStatus(targetStatus);

        return OrderStatusUpdateResponseDto.from(order);
    }

    // 현재 주문 상태에서 요청한 상태로 변경 가능한지 검증
    private void validateOrderStatus(Order order, OrderStatus targetStatus) {
        OrderStatus currentStatus = order.getOrderStatus();
        if (!currentStatus.canChangeTo(targetStatus)) {
            throw new CustomException(ErrorCode.ORDER_INVALID_STATUS_TRANSITION);
        }
    }
}
