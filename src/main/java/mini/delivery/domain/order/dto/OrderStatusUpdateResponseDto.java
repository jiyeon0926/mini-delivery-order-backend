package mini.delivery.domain.order.dto;

import lombok.Getter;
import mini.delivery.domain.order.entity.Order;

@Getter
public class OrderStatusUpdateResponseDto {

    private final Long orderId;
    private final String orderStatus;

    private OrderStatusUpdateResponseDto(Long orderId, String orderStatus) {
        this.orderId = orderId;
        this.orderStatus = orderStatus;
    }

    public static OrderStatusUpdateResponseDto from(Order order) {
        return new OrderStatusUpdateResponseDto(
                order.getId(),
                order.getOrderStatus().name()
        );
    }
}
