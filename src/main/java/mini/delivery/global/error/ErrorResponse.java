package mini.delivery.global.error;

import lombok.Getter;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

@Getter
public class ErrorResponse {

    private final String code;
    private final int status;
    private final String message;
    private final LocalDateTime timestamp;

    private ErrorResponse(String code, int status, String message, LocalDateTime timestamp) {
        this.code = code;
        this.status = status;
        this.message = message;
        this.timestamp = timestamp;
    }

    public static ResponseEntity<ErrorResponse> of(ErrorCode errorCode) {
        ErrorResponse errorResponse = new ErrorResponse(
                errorCode.name(),
                errorCode.getHttpStatus().value(),
                errorCode.getMessage(),
                LocalDateTime.now()
        );

        return ResponseEntity
                .status(errorCode.getHttpStatus())
                .body(errorResponse);
    }
}
