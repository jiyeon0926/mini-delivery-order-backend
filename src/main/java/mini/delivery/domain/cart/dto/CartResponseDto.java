package mini.delivery.domain.cart.dto;

import lombok.Getter;
import mini.delivery.domain.cart.entity.Cart;

import java.util.List;

@Getter
public class CartResponseDto {

    private final Long cartId;
    private final Long storeId;
    private final String storeName;
    private final List<CartItemResponseDto> items;
    private final int totalAmount;

    private CartResponseDto(Long cartId, Long storeId, String storeName, List<CartItemResponseDto> items, int totalAmount) {
        this.cartId = cartId;
        this.storeId = storeId;
        this.storeName = storeName;
        this.items = items;
        this.totalAmount = totalAmount;
    }

    public static CartResponseDto from(Cart cart, List<CartItemResponseDto> items, int totalAmount) {
        return new CartResponseDto(
                cart.getId(),
                cart.getStore().getId(),
                cart.getStore().getName(),
                items,
                totalAmount
        );
    }
}
