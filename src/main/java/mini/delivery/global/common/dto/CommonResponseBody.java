package mini.delivery.global.common.dto;

import lombok.Getter;

/**
 * Response body에 담을 공통 객체
 * 모든 API를 일관된 형태로 응답하기 위해 생성
 */
@Getter
public class CommonResponseBody<T> {

    private final String message;
    private final T data;

    private CommonResponseBody(String message, T data) {
        this.message = message;
        this.data = data;
    }

    private CommonResponseBody(String message) {
        this(message, null);
    }

    public static <T> CommonResponseBody<T> success(String message, T data) {
        return new CommonResponseBody<>(message, data);
    }

    public static CommonResponseBody<Void> success(String message) {
        return new CommonResponseBody<>(message);
    }
}
