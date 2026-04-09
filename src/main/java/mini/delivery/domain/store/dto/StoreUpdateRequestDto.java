package mini.delivery.domain.store.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class StoreUpdateRequestDto {

    private final String name;
    private final String address;
}
