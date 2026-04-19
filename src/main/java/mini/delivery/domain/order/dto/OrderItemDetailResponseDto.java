package mini.delivery.domain.order.dto;

import lombok.Getter;
import mini.delivery.domain.order.entity.OrderItem;

@Getter
public class OrderItemDetailResponseDto {

    private final Long itemId;
    private final Long menuId;
    private final String menuName;
    private final int price;
    private final int quantity;
    private final int totalPrice;

    private OrderItemDetailResponseDto(Long itemId, Long menuId, String menuName, int price, int quantity, int totalPrice) {
        this.itemId = itemId;
        this.menuId = menuId;
        this.menuName = menuName;
        this.price = price;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    public static OrderItemDetailResponseDto from(OrderItem orderItem, int totalPrice) {
        return new OrderItemDetailResponseDto(
                orderItem.getId(),
                orderItem.getMenu().getId(),
                orderItem.getMenu().getName(),
                orderItem.getPrice(),
                orderItem.getQuantity(),
                totalPrice
        );
    }
}
