package mini.delivery.domain.menu.dto;

import lombok.Getter;
import mini.delivery.domain.menu.entity.Menu;

@Getter
public class MenuCreateResponseDto {

    private final Long id;

    private MenuCreateResponseDto(Long id) {
        this.id = id;
    }

    public static MenuCreateResponseDto from(Menu menu){
        return new MenuCreateResponseDto(menu.getId());
    }
}
