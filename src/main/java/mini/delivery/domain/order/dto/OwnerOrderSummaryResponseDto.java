package mini.delivery.domain.order.dto;

import lombok.Getter;
import mini.delivery.domain.order.entity.Order;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class OwnerOrderSummaryResponseDto {

    private final Long orderId;
    private final String orderNumber;
    private final int totalAmount;
    private final String orderStatus;
    private final LocalDateTime createdAt;

    private OwnerOrderSummaryResponseDto(Long orderId, String orderNumber, int totalAmount, String orderStatus, LocalDateTime createdAt) {
        this.orderId = orderId;
        this.orderNumber = orderNumber;
        this.totalAmount = totalAmount;
        this.orderStatus = orderStatus;
        this.createdAt = createdAt;
    }

    public static OwnerOrderSummaryResponseDto from(Order order) {
        return new OwnerOrderSummaryResponseDto(
                order.getId(),
                order.getOrderNumber(),
                order.getTotalAmount(),
                order.getOrderStatus().name(),
                order.getCreatedAt()
        );
    }

    public static List<OwnerOrderSummaryResponseDto> from(List<Order> order) {
        return order.stream()
                .map(OwnerOrderSummaryResponseDto::from)
                .toList();
    }
}
