package mini.delivery.domain.cart.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mini.delivery.domain.cart.dto.CartItemCreateRequestDto;
import mini.delivery.domain.cart.service.CartService;
import mini.delivery.global.auth.UserDetailsImpl;
import mini.delivery.global.common.dto.CommonResponseBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @PostMapping("/items")
    public ResponseEntity<CommonResponseBody<Void>> addCartItem(@Valid @RequestBody CartItemCreateRequestDto cartItemCreateRequestDto,
                                                                @AuthenticationPrincipal UserDetailsImpl userDetails) {
        cartService.addCartItem(
                cartItemCreateRequestDto.getMenuId(),
                cartItemCreateRequestDto.getQuantity(),
                userDetails.getUsername()
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(CommonResponseBody.success("장바구니에 담았습니다."));
    }
}
