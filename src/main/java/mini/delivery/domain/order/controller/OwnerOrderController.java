package mini.delivery.domain.order.controller;

import lombok.RequiredArgsConstructor;
import mini.delivery.domain.order.dto.OrderStatusUpdateRequestDto;
import mini.delivery.domain.order.dto.OrderStatusUpdateResponseDto;
import mini.delivery.domain.order.service.OwnerOrderService;
import mini.delivery.global.auth.UserDetailsImpl;
import mini.delivery.global.common.dto.CommonResponseBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/owner/stores/{storeId}/orders")
@RequiredArgsConstructor
public class OwnerOrderController {

    private final OwnerOrderService ownerOrderService;

    @PatchMapping("/{orderId}/status")
    public ResponseEntity<CommonResponseBody<OrderStatusUpdateResponseDto>> updateOrderStatus(@PathVariable Long storeId,
                                                                                              @PathVariable Long orderId,
                                                                                              @RequestBody OrderStatusUpdateRequestDto orderStatusUpdateRequestDto,
                                                                                              @AuthenticationPrincipal UserDetailsImpl userDetails) {
        OrderStatusUpdateResponseDto orderStatusUpdateResponseDto = ownerOrderService.updateOrderStatus(
                storeId,
                orderId,
                orderStatusUpdateRequestDto.getOrderStatus(),
                userDetails.getUsername()
        );

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(CommonResponseBody.success("주문 상태를 변경하였습니다.", orderStatusUpdateResponseDto));
    }
}