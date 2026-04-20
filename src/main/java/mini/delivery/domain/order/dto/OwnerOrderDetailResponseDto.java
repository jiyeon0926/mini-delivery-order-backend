package mini.delivery.domain.order.dto;

import lombok.Getter;
import mini.delivery.domain.order.entity.Order;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class OwnerOrderDetailResponseDto {

    private final Long orderId;
    private final String orderNumber;
    private final int totalAmount;
    private final String customerAddress;
    private final String orderStatus;
    private final String rejectionReason;
    private final LocalDateTime createdAt;
    private final List<OrderItemDetailResponseDto> items;

    private OwnerOrderDetailResponseDto(Long orderId, String orderNumber, int totalAmount, String customerAddress, String orderStatus, String rejectionReason, LocalDateTime createdAt, List<OrderItemDetailResponseDto> items) {
        this.orderId = orderId;
        this.orderNumber = orderNumber;
        this.totalAmount = totalAmount;
        this.customerAddress = customerAddress;
        this.orderStatus = orderStatus;
        this.rejectionReason = rejectionReason;
        this.createdAt = createdAt;
        this.items = items;
    }

    public static OwnerOrderDetailResponseDto from(Order order, List<OrderItemDetailResponseDto> items) {
        return new OwnerOrderDetailResponseDto(
                order.getId(),
                order.getOrderNumber(),
                order.getTotalAmount(),
                order.getAddress(),
                order.getOrderStatus().name(),
                order.getRejectionReason(),
                order.getCreatedAt(),
                items
        );
    }
}
