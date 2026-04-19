package mini.delivery.domain.order.dto;

import lombok.Getter;
import mini.delivery.domain.order.entity.OrderItem;

@Getter
public class CustomerOrderItemDetailResponseDto {

    private final Long itemId;
    private final Long menuId;
    private final String menuName;
    private final int price;
    private final int quantity;
    private final int totalPrice;

    private CustomerOrderItemDetailResponseDto(Long itemId, Long menuId, String menuName, int price, int quantity, int totalPrice) {
        this.itemId = itemId;
        this.menuId = menuId;
        this.menuName = menuName;
        this.price = price;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    public static CustomerOrderItemDetailResponseDto from(OrderItem orderItem, int totalPrice) {
        return new CustomerOrderItemDetailResponseDto(
                orderItem.getId(),
                orderItem.getMenu().getId(),
                orderItem.getMenu().getName(),
                orderItem.getPrice(),
                orderItem.getQuantity(),
                totalPrice
        );
    }
}
