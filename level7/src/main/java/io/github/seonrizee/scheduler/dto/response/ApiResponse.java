package io.github.seonrizee.scheduler.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@AllArgsConstructor
public class ApiResponse<T> {

    private static final String SUCCESS_MESSAGE = "요청을 성공적으로 처리했습니다.";

    private int statusCode;
    private String message;
    private T data;

    public ApiResponse(HttpStatus status, String message, T data) {
        this.statusCode = status.value();
        this.message = message;
        this.data = data;
    }

    // 200 OK (데이터 포함)
    public static <T> ResponseEntity<ApiResponse<T>> ok(T data) {
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK, SUCCESS_MESSAGE, data));
    }

    // 200 OK (데이터 없음)
    public static ResponseEntity<ApiResponse<Void>> ok() {
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK, SUCCESS_MESSAGE, null));
    }

    // 201 Created
    public static <T> ResponseEntity<ApiResponse<T>> created(T data) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiResponse<>(HttpStatus.CREATED, SUCCESS_MESSAGE, data));
    }

    // 400 Bad Request
    public static ResponseEntity<ApiResponse<Void>> badRequest(String message) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponse<>(HttpStatus.BAD_REQUEST, message, null));
    }

    // 404 Not Found
    public static ResponseEntity<ApiResponse<Void>> notFound(String message) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ApiResponse<>(HttpStatus.NOT_FOUND, message, null));
    }
}
