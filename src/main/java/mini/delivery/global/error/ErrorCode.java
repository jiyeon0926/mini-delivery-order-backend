package mini.delivery.global.error;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@Getter
public enum ErrorCode {

    // 400 BAD_REQUEST
    PASSWORD_NOT_MATCH(BAD_REQUEST, "비밀번호가 일치하지 않습니다."),
    STORE_NOT_OPEN(BAD_REQUEST, "영업 중이 아닙니다."),

    // 404 NOT_FOUND
    USER_NOT_FOUND(NOT_FOUND, "사용자를 찾을 수 없습니다."),
    STORE_NOT_FOUND(NOT_FOUND, "가게를 찾을 수 없습니다."),
    MENU_NOT_FOUND(NOT_FOUND, "메뉴를 찾을 수 없습니다."),
    CART_NOT_FOUND(NOT_FOUND, "장바구니를 찾을 수 없습니다."),
    CART_ITEM_NOT_FOUND(NOT_FOUND, "장바구니에 담긴 메뉴를 찾을 수 없습니다."),

    // 409 CONFLICT
    EMAIL_ALREADY_EXISTS(CONFLICT, "이미 사용 중인 이메일입니다."),
    STORE_LIMIT_EXCEEDED(CONFLICT, "가게는 최대 3개까지 운영할 수 있습니다."),
    CART_ITEM_ALREADY_EXISTS(CONFLICT, "이미 장바구니에 담긴 메뉴입니다.");

    private final HttpStatus httpStatus;
    private final String message;

    ErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
