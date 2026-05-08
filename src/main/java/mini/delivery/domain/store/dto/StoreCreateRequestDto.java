package mini.delivery.domain.store.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalTime;

@Getter
@RequiredArgsConstructor
public class StoreCreateRequestDto {

    @NotBlank
    @Size(max = 40)
    private final String name;

    @NotBlank
    @Size(max = 50)
    private final String address;

    @NotNull
    private final int minOrderAmount;

    @NotNull
    private final LocalTime openTime;

    @NotNull
    private final LocalTime closeTime;
}
