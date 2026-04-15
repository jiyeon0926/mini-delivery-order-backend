package mini.delivery.domain.store.dto;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalTime;

@Getter
@RequiredArgsConstructor
public class StoreStatusChangeDto {

    private final String storeStatus;
}
