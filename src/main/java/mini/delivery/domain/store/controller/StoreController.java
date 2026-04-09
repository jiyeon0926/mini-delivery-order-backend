package mini.delivery.domain.store.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import mini.delivery.domain.store.dto.StoreCreateRequestDto;
import mini.delivery.domain.store.dto.StoreCreateResponseDto;
import mini.delivery.domain.store.service.StoreService;
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
@RequestMapping("/api")
@RequiredArgsConstructor
public class StoreController {

    private final StoreService storeService;

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
}
