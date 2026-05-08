package mini.delivery.domain.menu.dto;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MenuUpdateRequestDto {

    @Size(max = 40)
    private  final String name;

    private final Integer price;
}
