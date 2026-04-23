package mini.delivery.domain.order.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mini.delivery.domain.order.dto.*;
import mini.delivery.domain.order.service.OwnerOrderService;
import mini.delivery.global.auth.UserDetailsImpl;
import mini.delivery.global.common.dto.CommonResponseBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/owner")
@RequiredArgsConstructor
public class OwnerOrderController {

    private final OwnerOrderService ownerOrderService;

    @PatchMapping("/stores/{storeId}/orders/{orderId}/status")
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

    @PatchMapping("/stores/{storeId}/orders/{orderId}/reject")
    public ResponseEntity<CommonResponseBody<OrderRejectResponseDto>> rejectOrder(@PathVariable Long storeId,
                                                                                  @PathVariable Long orderId,
                                                                                  @Valid @RequestBody OrderRejectRequestDto orderRejectRequestDto,
                                                                                  @AuthenticationPrincipal UserDetailsImpl userDetails) {
        OrderRejectResponseDto orderRejectResponseDto = ownerOrderService.rejectOrder(
                storeId,
                orderId,
                orderRejectRequestDto.getRejectionReason(),
                userDetails.getUsername()
        );

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(CommonResponseBody.success("주문을 거절하였습니다.", orderRejectResponseDto));
    }

    @GetMapping("/stores/{storeId}/orders")
    public ResponseEntity<CommonResponseBody<OwnerStoreOrderResponseDto>> getOrdersByStoreId(@PathVariable Long storeId,
                                                                                             @AuthenticationPrincipal UserDetailsImpl userDetails) {
        OwnerStoreOrderResponseDto ownerStoreOrderResponseDto = ownerOrderService.getOrdersByStoreId(storeId, userDetails.getUsername());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(CommonResponseBody.success("가게 주문 목록 조회를 성공하였습니다.", ownerStoreOrderResponseDto));
    }

    @GetMapping("/stores/{storeId}/orders/{orderId}")
    public ResponseEntity<CommonResponseBody<OwnerOrderDetailResponseDto>> getOrderDetail(@PathVariable Long storeId,
                                                                                          @PathVariable Long orderId,
                                                                                          @AuthenticationPrincipal UserDetailsImpl userDetails) {
        OwnerOrderDetailResponseDto ownerOrderDetailResponseDto = ownerOrderService.getOrderDetail(
                storeId,
                orderId,
                userDetails.getUsername()
        );

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(CommonResponseBody.success("가게 주문 조회를 성공하였습니다.", ownerOrderDetailResponseDto));
    }

    @GetMapping("/orders/count")
    public ResponseEntity<CommonResponseBody<DailyOrderCountResponseDto>> getAllOrderCountByStatus(@RequestParam(value = "date", required = false) LocalDate date,
                                                                                                   @AuthenticationPrincipal UserDetailsImpl userDetails) {
        DailyOrderCountResponseDto dailyOrderCountResponseDto = ownerOrderService.getAllOrderCountByStatus(date, userDetails.getUsername());

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(CommonResponseBody.success("주문 수 조회를 성공하였습니다.", dailyOrderCountResponseDto));
    }
}