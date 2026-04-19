package mini.delivery.domain.order.dto;

import lombok.Getter;
import mini.delivery.domain.order.entity.OrderItem;

import java.util.List;

@Getter
public class CustomerOrderItemResponseDto {

    private final Long itemId;
    private final Long menuId;
    private final String menuName;
    private final int quantity;

    private CustomerOrderItemResponseDto(Long itemId, Long menuId, String menuName, int quantity) {
        this.itemId = itemId;
        this.menuId = menuId;
        this.menuName = menuName;
        this.quantity = quantity;
    }

    public static CustomerOrderItemResponseDto from(OrderItem orderItem) {
        return new CustomerOrderItemResponseDto(
                orderItem.getId(),
                orderItem.getMenu().getId(),
                orderItem.getMenu().getName(),
                orderItem.getQuantity()
        );
    }

    public static List<CustomerOrderItemResponseDto> from(List<OrderItem> orderItems) {
        return orderItems.stream()
                .map(CustomerOrderItemResponseDto::from)
                .toList();
    }
}
