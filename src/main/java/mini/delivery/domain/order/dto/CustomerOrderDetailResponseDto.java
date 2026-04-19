package mini.delivery.domain.order.dto;

import lombok.Getter;
import mini.delivery.domain.order.entity.Order;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class CustomerOrderDetailResponseDto {

    private final Long orderId;
    private final Long storeId;
    private final String storeName;
    private final String orderNumber;
    private final String customerAddress;
    private final String orderStatus;
    private final String rejectionReason;
    private final LocalDateTime createdAt;
    private final int totalAmount;
    private final List<OrderItemDetailResponseDto> items;

    private CustomerOrderDetailResponseDto(Long orderId, Long storeId, String storeName, String orderNumber, String customerAddress, String orderStatus, String rejectionReason, LocalDateTime createdAt, int totalAmount, List<OrderItemDetailResponseDto> items) {
        this.orderId = orderId;
        this.storeId = storeId;
        this.storeName = storeName;
        this.orderNumber = orderNumber;
        this.customerAddress = customerAddress;
        this.orderStatus = orderStatus;
        this.rejectionReason = rejectionReason;
        this.createdAt = createdAt;
        this.totalAmount = totalAmount;
        this.items = items;
    }

    public static CustomerOrderDetailResponseDto from(Order order, List<OrderItemDetailResponseDto> items) {
        return new CustomerOrderDetailResponseDto(
                order.getId(),
                order.getStore().getId(),
                order.getStore().getName(),
                order.getOrderNumber(),
                order.getAddress(),
                order.getOrderStatus().name(),
                order.getRejectionReason(),
                order.getCreatedAt(),
                order.getTotalAmount(),
                items
        );
    }
}
