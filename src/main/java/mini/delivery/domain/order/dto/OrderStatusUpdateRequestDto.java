package mini.delivery.domain.order.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class OrderStatusUpdateRequestDto {

    private final String orderStatus;
}
