package mini.delivery.domain.order.dto;

import lombok.Getter;
import mini.delivery.domain.store.entity.Store;

import java.util.List;

@Getter
public class OwnerStoreOrderResponseDto {

    private final Long storeId;
    private final String storeName;
    private final List<OwnerOrderSummaryResponseDto> orders;

    private OwnerStoreOrderResponseDto(Long storeId, String storeName, List<OwnerOrderSummaryResponseDto> orders) {
        this.storeId = storeId;
        this.storeName = storeName;
        this.orders = orders;
    }

    public static OwnerStoreOrderResponseDto from(Store store, List<OwnerOrderSummaryResponseDto> orders) {
        return new OwnerStoreOrderResponseDto(
                store.getId(),
                store.getName(),
                orders
        );
    }
}
