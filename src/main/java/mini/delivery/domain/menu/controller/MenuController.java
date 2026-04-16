package mini.delivery.domain.menu.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mini.delivery.domain.menu.dto.MenuCreateRequestDto;
import mini.delivery.domain.menu.dto.MenuCreateResponseDto;
import mini.delivery.domain.menu.dto.MenuUpdateRequestDto;
import mini.delivery.domain.menu.service.MenuService;
import mini.delivery.global.auth.UserDetailsImpl;
import mini.delivery.global.common.dto.CommonResponseBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class MenuController {

    private final MenuService menuService;
    // 메뉴 생성
    @PostMapping("/owner/stores/{storeId}/menu")
    public ResponseEntity<CommonResponseBody<MenuCreateResponseDto>> createMenu(@PathVariable Long storeId,
                                                                                @Valid @RequestBody MenuCreateRequestDto menuCreateRequestDto,
                                                                               @AuthenticationPrincipal UserDetailsImpl userDetails) {
        MenuCreateResponseDto menuCreateResponseDto = menuService.createMenu(
                storeId,
                menuCreateRequestDto,
                userDetails.getUsername()
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(CommonResponseBody.success("메뉴를 생성하였습니다.", menuCreateResponseDto));
    }
    // 메뉴 수정
    @PatchMapping("/owner/stores/{storeId}/menu/{menuId}")
    public ResponseEntity<CommonResponseBody<Void>> updateMenu(@PathVariable Long storeId,
                                                                                @PathVariable Long menuId,
                                                                                @Valid @RequestBody MenuUpdateRequestDto menuUpdateRequestDto,
                                                                                @AuthenticationPrincipal UserDetailsImpl userDetails) {
        menuService.updateMenu(
                storeId,
                menuId,
                menuUpdateRequestDto,
                userDetails.getUsername()
        );

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(CommonResponseBody.success("메뉴를 수정하였습니다."));
    }
}
