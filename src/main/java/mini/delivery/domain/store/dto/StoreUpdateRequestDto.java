package mini.delivery.domain.store.dto;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalTime;

@Getter
@RequiredArgsConstructor
public class StoreUpdateRequestDto {

    @Size(max = 40)
    private final String name;

    @Size(max = 50)
    private final String address;
    private final Integer minOrderAmount;
    private final LocalTime openTime;
    private final LocalTime closeTime;
}
