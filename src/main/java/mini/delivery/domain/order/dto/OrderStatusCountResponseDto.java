package mini.delivery.domain.order.dto;

import lombok.Getter;
import mini.delivery.global.common.enums.OrderStatus;

import java.util.Map;

@Getter
public class OrderStatusCountResponseDto {

    private final long pending;
    private final long cooking;
    private final long delivering;
    private final long delivered;
    private final long rejected;

    private OrderStatusCountResponseDto(long pending, long cooking, long delivering, long delivered, long rejected) {
        this.pending = pending;
        this.cooking = cooking;
        this.delivering = delivering;
        this.delivered = delivered;
        this.rejected = rejected;
    }

    public static OrderStatusCountResponseDto from(Map<OrderStatus, Long> map) {
        return new OrderStatusCountResponseDto(
                map.getOrDefault(OrderStatus.PENDING, 0L),
                map.getOrDefault(OrderStatus.COOKING, 0L),
                map.getOrDefault(OrderStatus.DELIVERING, 0L),
                map.getOrDefault(OrderStatus.DELIVERED, 0L),
                map.getOrDefault(OrderStatus.REJECTED, 0L)
        );
    }
}
