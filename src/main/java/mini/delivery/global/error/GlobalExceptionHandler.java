package mini.delivery.global.error;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HandlerMethodValidationException.class)
    protected ResponseEntity<String> handleMethodValidationException(HandlerMethodValidationException e) {
        String message = e.getParameterValidationResults()
                .getFirst()
                .getResolvableErrors()
                .getFirst()
                .getDefaultMessage();

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(message);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<String> handleValidationException(MethodArgumentNotValidException e) {
        String message = e.getBindingResult()
                .getAllErrors()
                .getFirst()
                .getDefaultMessage();

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(message);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(e.getMessage());
    }

    @ExceptionHandler(AuthenticationException.class)
    protected ResponseEntity<String> handleAuthException(AuthenticationException e) {
        HttpStatus statusCode = e instanceof BadCredentialsException
                ? HttpStatus.FORBIDDEN
                : HttpStatus.UNAUTHORIZED;

        return ResponseEntity
                .status(statusCode)
                .body(e.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    protected ResponseEntity<String> handleAccessDeniedException(AccessDeniedException e) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(e.getMessage());
    }

    @ExceptionHandler(AuthorizationDeniedException.class)
    protected ResponseEntity<String> handleAuthorizationDeniedException(AuthorizationDeniedException e) {
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(e.getMessage());
    }

    @ExceptionHandler(JwtException.class)
    protected ResponseEntity<String> handleJwtException(JwtException e) {
        HttpStatus httpStatus;

        if (e instanceof ExpiredJwtException) {
            httpStatus = HttpStatus.UNAUTHORIZED;
        } else if (e instanceof MalformedJwtException) {
            httpStatus = HttpStatus.BAD_REQUEST;
        } else {
            httpStatus = HttpStatus.UNAUTHORIZED;
        }

        return ResponseEntity
                .status(httpStatus)
                .body(e.getMessage());
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<String> handleUsernameNotFoundException(UsernameNotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(e.getMessage());
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(CustomException e) {
        return ErrorResponse.of(e.getErrorCode());
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<String> handleException(Exception e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(e.getMessage());
    }
}
