package mini.delivery.domain.order.dto;

import lombok.Getter;
import mini.delivery.domain.order.entity.Order;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class CustomerOrderResponseDto {

    private final Long orderId;
    private final Long storeId;
    private final String storeName;
    private final LocalDateTime createdAt;
    private final String orderStatus;
    private final int totalAmount;
    private final List<CustomerOrderItemResponseDto> items;

    private CustomerOrderResponseDto(Long orderId, Long storeId, String storeName, LocalDateTime createdAt, String orderStatus, int totalAmount, List<CustomerOrderItemResponseDto> items) {
        this.orderId = orderId;
        this.storeId = storeId;
        this.storeName = storeName;
        this.createdAt = createdAt;
        this.orderStatus = orderStatus;
        this.totalAmount = totalAmount;
        this.items = items;
    }

    public static CustomerOrderResponseDto from(Order order, int totalAmount, List<CustomerOrderItemResponseDto> items) {
        return new CustomerOrderResponseDto(
                order.getId(),
                order.getStore().getId(),
                order.getStore().getName(),
                order.getCreatedAt(),
                order.getOrderStatus().name(),
                totalAmount,
                items
        );
    }
}
