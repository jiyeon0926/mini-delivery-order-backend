package mini.delivery.domain.cart.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CartItemCreateRequestDto {

    @NotNull
    private final Long menuId;

    @NotNull
    @Min(value = 1)
    private final int quantity;
}
