package mini.delivery.domain.menu.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MenuCreateRequestDto {
    @NotBlank
    @Size(max = 20)
    private final String name;

    @NotNull
    private final int price;
}
