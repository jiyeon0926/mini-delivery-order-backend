package mini.delivery.domain.order.dto;

import lombok.Getter;
import mini.delivery.domain.order.entity.Order;

@Getter
public class OrderCreateResponseDto {

    private final Long orderId;

    private OrderCreateResponseDto(Long orderId) {
        this.orderId = orderId;
    }

    public static OrderCreateResponseDto from(Order order) {
        return new OrderCreateResponseDto(order.getId());
    }
}
