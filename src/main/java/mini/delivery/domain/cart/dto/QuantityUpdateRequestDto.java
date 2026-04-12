package mini.delivery.domain.cart.dto;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class QuantityUpdateRequestDto {

    @Min(value = 1)
    private final int quantity;
}
