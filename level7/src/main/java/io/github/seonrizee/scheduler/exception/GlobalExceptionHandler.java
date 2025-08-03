package io.github.seonrizee.scheduler.exception;

import io.github.seonrizee.scheduler.dto.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ScheduleNotFoundException.class)
    public ResponseEntity<ApiResponse<Void>> handleScheduleNotFoundException(ScheduleNotFoundException e) {

        return ApiResponse.notFound(e.getMessage());
    }

    @ExceptionHandler(InvalidPasswordException.class)
    public ResponseEntity<ApiResponse<Void>> handleInvalidPasswordException(InvalidPasswordException e) {

        return ApiResponse.badRequest(e.getMessage());
    }

    @ExceptionHandler(CommentLimitExceededException.class)
    public ResponseEntity<ApiResponse<Void>> handleCommentLimitExceededException(CommentLimitExceededException e) {

        return ApiResponse.badRequest(e.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<Void>> handleIllegalArgumentException(IllegalArgumentException e) {

        return ApiResponse.badRequest(e.getMessage());
    }
}
