package io.github.seonrizee.scheduler.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

/**
 * API 응답을 위한 공통 래퍼 클래스
 *
 * @param <T> 응답 데이터의 타입
 */
@Getter
@AllArgsConstructor
public class ApiResponse<T> {

    private static final String SUCCESS_MESSAGE = "요청을 성공적으로 처리했습니다.";

    // HTTP 응답 상태 코드
    private int statusCode;
    private String message;
    private T data;

    public ApiResponse(HttpStatus status, String message, T data) {
        this.statusCode = status.value();
        this.message = message;
        this.data = data;
    }

    /**
     * 200 OK 응답을 생성합니다. (데이터 포함)
     *
     * @param data 응답에 포함될 데이터
     * @return 데이터가 포함된 ResponseEntity
     */
    public static <T> ResponseEntity<ApiResponse<T>> ok(T data) {
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK, SUCCESS_MESSAGE, data));
    }

    /**
     * 200 OK 응답을 생성합니다. (데이터 없음)
     *
     * @return 데이터가 없는 ResponseEntity
     */
    public static ResponseEntity<ApiResponse<Void>> ok() {
        return ResponseEntity.ok(new ApiResponse<>(HttpStatus.OK, SUCCESS_MESSAGE, null));
    }

    /**
     * 201 Created 응답을 생성합니다.
     *
     * @param data 생성된 리소스 데이터
     * @return 데이터가 포함된 ResponseEntity
     */
    public static <T> ResponseEntity<ApiResponse<T>> created(T data) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ApiResponse<>(HttpStatus.CREATED, SUCCESS_MESSAGE, data));
    }

    /**
     * 400 Bad Request 응답을 생성합니다.
     *
     * @param message 에러 메시지
     * @return 에러 메시지가 포함된 ResponseEntity
     */
    public static ResponseEntity<ApiResponse<Void>> badRequest(String message) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponse<>(HttpStatus.BAD_REQUEST, message, null));
    }

    /**
     * 404 Not Found 응답을 생성합니다.
     *
     * @param message 에러 메시지
     * @return 에러 메시지가 포함된 ResponseEntity
     */
    public static ResponseEntity<ApiResponse<Void>> notFound(String message) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ApiResponse<>(HttpStatus.NOT_FOUND, message, null));
    }
}
