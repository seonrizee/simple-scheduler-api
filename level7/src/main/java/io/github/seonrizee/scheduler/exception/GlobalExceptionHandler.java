package io.github.seonrizee.scheduler.exception;

import io.github.seonrizee.scheduler.dto.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 애플리케이션 전역에서 발생하는 예외를 처리하는 핸들러. RestControllerAdvice를 사용하여 모든 컨트롤러에서 발생하는 예외를 일관된 형식으로 응답.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 요청한 리소스를 찾을 수 없을 때 발생하는 예외를 처리합니다.
     *
     * @param e 발생한 ScheduleNotFoundException
     * @return 404 Not Found 상태 코드와 에러 메시지를 담은 응답
     */
    @ExceptionHandler(ScheduleNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleScheduleNotFoundException(ScheduleNotFoundException e) {
        return ApiResponse.notFound(e.getMessage());
    }

    /**
     * 비밀번호가 일치하지 않을 때 발생하는 예외를 처리합니다.
     *
     * @param e 발생한 InvalidPasswordException
     * @return 400 Bad Request 상태 코드와 에러 메시지를 담은 응답
     */
    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<ApiResponse<Void>> handleInvalidPasswordException(InvalidPasswordException e) {
        return ApiResponse.badRequest(e.getMessage());
    }

    /**
     * 하나의 일정에 작성할 수 있는 최대 댓글 개수를 초과했을 때 발생하는 예외를 처리합니다.
     *
     * @param e 발생한 CommentLimitExceededException
     * @return 400 Bad Request 상태 코드와 에러 메시지를 담은 응답
     */
    @ExceptionHandler(CommentLimitExceededException.class)
    public ResponseEntity<ApiResponse<Void>> handleCommentLimitExceededException(CommentLimitExceededException e) {

        return ApiResponse.badRequest(e.getMessage());
    }

    /**
     * 유효성 검사 실패 또는 잘못된 인자 값으로 인해 발생하는 예외를 처리합니다.
     *
     * @param e 발생한 IllegalArgumentException
     * @return 400 Bad Request 상태 코드와 에러 메시지를 담은 응답
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<Void>> handleIllegalArgumentException(IllegalArgumentException e) {
        return ApiResponse.badRequest(e.getMessage());
    }
}