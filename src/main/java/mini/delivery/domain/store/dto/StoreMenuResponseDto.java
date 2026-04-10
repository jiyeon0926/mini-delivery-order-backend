package mini.delivery.domain.store.dto;

import lombok.Getter;
import mini.delivery.domain.menu.entity.Menu;

import java.util.List;

@Getter
public class StoreMenuResponseDto {

    private final String menuName;
    private final int price;

    private StoreMenuResponseDto(String menuName, int price) {
        this.menuName = menuName;
        this.price = price;
    }

    public static StoreMenuResponseDto from(Menu menu) {
        return new StoreMenuResponseDto(menu.getName(), menu.getPrice());
    }

    public static List<StoreMenuResponseDto> from(List<Menu> menus) {
        return menus.stream()
                .map(StoreMenuResponseDto::from)
                .toList();
    }
}
