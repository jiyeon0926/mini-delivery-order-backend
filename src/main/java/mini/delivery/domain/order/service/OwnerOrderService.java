package mini.delivery.domain.order.service;

import lombok.RequiredArgsConstructor;
import mini.delivery.domain.order.dto.*;
import mini.delivery.domain.order.entity.Order;
import mini.delivery.domain.order.entity.OrderItem;
import mini.delivery.domain.order.repository.OrderItemRepository;
import mini.delivery.domain.order.repository.OrderRepository;
import mini.delivery.domain.order.repository.OrderStatusAndCountOnly;
import mini.delivery.domain.store.entity.Store;
import mini.delivery.domain.store.repository.StoreRepository;
import mini.delivery.domain.user.entity.User;
import mini.delivery.domain.user.repository.UserRepository;
import mini.delivery.global.common.enums.OrderStatus;
import mini.delivery.global.error.CustomException;
import mini.delivery.global.error.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OwnerOrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;

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

    @Transactional
    public OrderRejectResponseDto rejectOrder(Long storeId, Long orderId, String rejectionReason, String email) {
        User user = userRepository.findByEmailAndIsDeletedFalse(email)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        Order order = orderRepository.findByIdAndStoreIdAndOwnerId(orderId, storeId, user.getId())
                .orElseThrow(() -> new CustomException(ErrorCode.ORDER_NOT_FOUND));

        validatePendingStatus(order);

        order.updateOrderStatus(OrderStatus.REJECTED);
        order.rejectionReason(rejectionReason);

        return OrderRejectResponseDto.from(order);
    }

    @Transactional(readOnly = true)
    public OwnerStoreOrderResponseDto getOrdersByStoreId(Long storeId, String email) {
        User user = userRepository.findByEmailAndIsDeletedFalse(email)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        Store store = storeRepository.findByIdAndUserId(storeId, user.getId())
                .orElseThrow(() -> new CustomException(ErrorCode.STORE_NOT_FOUND));
        List<Order> orders = orderRepository.findAllByStoreId(storeId);

        return OwnerStoreOrderResponseDto.from(store, OwnerOrderSummaryResponseDto.from(orders));
    }

    @Transactional(readOnly = true)
    public OwnerOrderDetailResponseDto getOrderDetail(Long storeId, Long orderId, String email) {
        User user = userRepository.findByEmailAndIsDeletedFalse(email)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));
        Order order = orderRepository.findByIdAndStoreIdAndOwnerId(orderId, storeId, user.getId())
                .orElseThrow(() -> new CustomException(ErrorCode.ORDER_NOT_FOUND));

        List<OrderItem> orderItems = orderItemRepository.findAllByOrderIdWithMenu(orderId);
        List<OrderItemDetailResponseDto> orderItemDetailResponseDtoList = toOrderItemDetailResponses(orderItems);

        return OwnerOrderDetailResponseDto.from(order, orderItemDetailResponseDtoList);
    }

    @Transactional(readOnly = true)
    public DailyOrderCountResponseDto getAllOrderCountByStatus(LocalDate date, String email) {
        User user = userRepository.findByEmailAndIsDeletedFalse(email)
                .orElseThrow(() -> new CustomException(ErrorCode.USER_NOT_FOUND));

        ZoneId zone = ZoneId.of("Asia/Seoul");
        LocalDateTime start = (date != null) ? date.atStartOfDay() : LocalDate.now(zone).atStartOfDay();
        LocalDateTime end = start.plusDays(1);

        Map<OrderStatus, Long> map = getOrderStatusCountMap(user.getId(), start, end);

        return DailyOrderCountResponseDto.from(start.toLocalDate(), OrderStatusCountResponseDto.from(map));
    }

    // 현재 주문 상태에서 요청한 상태로 변경 가능한지 검증
    private void validateOrderStatus(Order order, OrderStatus targetStatus) {
        OrderStatus currentStatus = order.getOrderStatus();
        if (!currentStatus.canChangeTo(targetStatus)) {
            throw new CustomException(ErrorCode.ORDER_INVALID_STATUS_TRANSITION);
        }
    }

    private void validatePendingStatus(Order order) {
        if (order.isNotPendingStatus()) {
            throw new CustomException(ErrorCode.ORDER_NOT_IN_PENDING_STATUS);
        }
    }

    private List<OrderItemDetailResponseDto> toOrderItemDetailResponses(List<OrderItem> items) {
        return items.stream()
                .map(item -> {
                    int totalPrice = item.getPrice() * item.getQuantity();

                    return OrderItemDetailResponseDto.from(item, totalPrice);
                })
                .toList();
    }

    private Map<OrderStatus, Long> getOrderStatusCountMap(Long userId, LocalDateTime start, LocalDateTime end) {
        List<OrderStatusAndCountOnly> results = orderRepository.getCountGroupByStatusByOwnerIdAndDate(userId, start, end);
        Map<OrderStatus, Long> map = new EnumMap<>(OrderStatus.class);
        results.forEach(result -> map.put(result.getOrderStatus(), result.getCount()));

        return map;
    }
}
