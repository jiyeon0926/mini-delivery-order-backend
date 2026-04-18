package mini.delivery.domain.order.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class OrderRejectRequestDto {

    @NotBlank
    @Size(max = 200)
    private final String rejectionReason;
}
