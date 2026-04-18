package mini.delivery.domain.order.dto;

import lombok.Getter;
import mini.delivery.domain.order.entity.Order;

import java.time.LocalDateTime;

@Getter
public class OrderRejectResponseDto {

    private final Long orderId;
    private final String orderStatus;
    private final String rejectionReason;
    private final LocalDateTime rejectedAt;

    private OrderRejectResponseDto(Long orderId, String orderStatus, String rejectionReason, LocalDateTime rejectedAt) {
        this.orderId = orderId;
        this.orderStatus = orderStatus;
        this.rejectionReason = rejectionReason;
        this.rejectedAt = rejectedAt;
    }

    public static OrderRejectResponseDto from(Order order) {
        return new OrderRejectResponseDto(
                order.getId(),
                order.getOrderStatus().name(),
                order.getRejectionReason(),
                order.getUpdatedAt()
        );
    }
}
