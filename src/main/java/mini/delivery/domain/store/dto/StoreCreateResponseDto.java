package mini.delivery.domain.store.dto;

import lombok.Getter;
import mini.delivery.domain.store.entity.Store;

@Getter
public class StoreCreateResponseDto {

    private final Long id;

    private StoreCreateResponseDto(Long id) {
        this.id = id;
    }

    public static StoreCreateResponseDto from(Store store) {
        return new StoreCreateResponseDto(store.getId());
    }
}
