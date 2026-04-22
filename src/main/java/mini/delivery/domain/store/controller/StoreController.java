package mini.delivery.domain.store.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mini.delivery.domain.store.dto.*;
import mini.delivery.domain.store.service.StoreService;
import mini.delivery.global.auth.UserDetailsImpl;
import mini.delivery.global.common.dto.CommonResponseBody;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;
    // 가게 생성
    @PostMapping("/owner/stores")
    public ResponseEntity<CommonResponseBody<StoreCreateResponseDto>> createStore(@Valid @RequestBody StoreCreateRequestDto storeCreateRequestDto,
                                                                                  @AuthenticationPrincipal UserDetailsImpl userDetails) {
        StoreCreateResponseDto storeCreateResponseDto = storeService.createStore(
                storeCreateRequestDto,
                userDetails.getUsername()
        );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(CommonResponseBody.success("가게를 생성하였습니다.", storeCreateResponseDto));
    }
    // 가게 수정
    @PatchMapping("/owner/stores/{storeId}")
    public ResponseEntity<CommonResponseBody<Void>> updateStore(@PathVariable Long storeId,
                                                                @Valid @RequestBody StoreUpdateRequestDto storeUpdateRequestDto,
                                                                @AuthenticationPrincipal UserDetailsImpl userDetails) {
        storeService.updateStore(
                storeId,
                storeUpdateRequestDto,
                userDetails.getUsername()
        );

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(CommonResponseBody.success("가게를 수정하였습니다."));
    }

    @GetMapping("/stores/{storeId}")
    public ResponseEntity<CommonResponseBody<StoreResponseDto>> getStoreById(@PathVariable Long storeId) {
        StoreResponseDto storeResponseDto = storeService.getStoreById(storeId);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(CommonResponseBody.success("가게 조회를 성공하였습니다.", storeResponseDto));
    }

    @GetMapping("/stores")
    public ResponseEntity<CommonResponseBody<List<StoreSummaryResponseDto>>> searchStores(@RequestParam(value = "keyword", required = false) String keyword) {
        List<StoreSummaryResponseDto> storeSummaryResponseDtoList = storeService.searchStores(keyword);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(CommonResponseBody.success("가게 목록 조회를 성공하였습니다.", storeSummaryResponseDtoList));
    }

    //가게 영업 상태 변경
    @PatchMapping("/owner/stores/{storeId}/status")
    public ResponseEntity<CommonResponseBody<StoreStatusChangeResponseDto>> storeStatus(@PathVariable Long storeId,
                                                                                        @RequestBody StoreStatusChangeDto storeStatusChangeDto,
                                                                                        @AuthenticationPrincipal UserDetailsImpl userDetails) {
        StoreStatusChangeResponseDto storeStatusChangeResponseDto = storeService.storeStatus(
                storeId,
                storeStatusChangeDto.getStoreStatus(),
                userDetails.getUsername()
        );

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(CommonResponseBody.success("가게 상태 변경을 성공하였습니다.", storeStatusChangeResponseDto));
    }

    //가게 폐업
    @DeleteMapping("/owner/stores/{storeId}")
    public ResponseEntity<Void> closeStore(@PathVariable Long storeId,
                                           @AuthenticationPrincipal UserDetailsImpl userDetails) {
        storeService.closeStore(
                storeId,
                userDetails.getUsername()
        );

        return ResponseEntity.noContent().build();
    }

    // 가게 다건 조회
    @GetMapping("/owner/stores")
    public ResponseEntity<CommonResponseBody<List<OwnerStoreResponseDto>>> allFindStore(@AuthenticationPrincipal UserDetailsImpl userDetails){

        List<OwnerStoreResponseDto> ownerStoreResponseDtoList = storeService.allFindStore(
                userDetails.getUsername()
        );

        return ResponseEntity.status(HttpStatus.OK).body(CommonResponseBody.success("가게 다건 조회를 성공 하였습니다.",ownerStoreResponseDtoList));
    }
}
