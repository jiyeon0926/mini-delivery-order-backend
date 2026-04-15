package mini.delivery.domain.store.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class StoreStatusChangeResponseDto {
    private final Long storeId;
    private final String storeStatus;
}
