package mini.delivery.domain.cart.dto;

import lombok.Getter;
import mini.delivery.domain.cart.entity.CartItem;

import java.util.List;

@Getter
public class CartItemResponseDto {

    private final Long itemId;
    private final Long menuId;
    private final String menuName;
    private final int price;
    private final int quantity;
    private final int totalPrice;

    private CartItemResponseDto(Long itemId, Long menuId, String menuName, int price, int quantity, int totalPrice) {
        this.itemId = itemId;
        this.menuId = menuId;
        this.menuName = menuName;
        this.price = price;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }

    public static CartItemResponseDto from(CartItem cartItem, int totalPrice) {
        return new CartItemResponseDto(
                cartItem.getId(),
                cartItem.getMenu().getId(),
                cartItem.getMenu().getName(),
                cartItem.getMenu().getPrice(),
                cartItem.getQuantity(),
                totalPrice
        );
    }

    public static List<CartItemResponseDto> from(List<CartItemResponseDto> cartItemResponseDtoList) {
        return cartItemResponseDtoList;
    }
}
