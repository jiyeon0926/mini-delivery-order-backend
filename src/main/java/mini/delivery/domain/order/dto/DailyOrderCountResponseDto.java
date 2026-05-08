package mini.delivery.domain.order.dto;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class DailyOrderCountResponseDto {

    private final LocalDate date;
    private final OrderStatusCountResponseDto count;

    private DailyOrderCountResponseDto(LocalDate date, OrderStatusCountResponseDto count) {
        this.date = date;
        this.count = count;
    }

    public static DailyOrderCountResponseDto from(LocalDate date, OrderStatusCountResponseDto count) {
        return new DailyOrderCountResponseDto(date, count);
    }
}
