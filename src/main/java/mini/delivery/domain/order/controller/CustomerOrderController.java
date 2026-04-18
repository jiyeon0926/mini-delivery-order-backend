package mini.delivery.domain.order.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mini.delivery.domain.order.dto.OrderCreateRequestDto;
import mini.delivery.domain.order.dto.OrderCreateResponseDto;
import mini.delivery.domain.order.service.CustomerOrderService;
import mini.delivery.global.auth.UserDetailsImpl;
import mini.delivery.global.common.dto.CommonResponseBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class CustomerOrderController {

    private final CustomerOrderService customerOrderService;

    @PostMapping
    public ResponseEntity<CommonResponseBody<OrderCreateResponseDto>> createOrder(@Valid @RequestBody OrderCreateRequestDto orderCreateRequestDto,
                                                                                  @AuthenticationPrincipal UserDetailsImpl userDetails) {
        OrderCreateResponseDto orderCreateResponseDto = customerOrderService.createOrder(
                orderCreateRequestDto.getAddress(),
                userDetails.getUsername()
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(CommonResponseBody.success("주문을 성공하였습니다.", orderCreateResponseDto));
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> cancelOrder(@PathVariable Long orderId,
                                            @AuthenticationPrincipal UserDetailsImpl userDetails) {
        customerOrderService.cancelOrder(orderId, userDetails.getUsername());

        return ResponseEntity.noContent().build();
    }
}
