package io.github.seonrizee.scheduler.exception;

import io.github.seonrizee.scheduler.dto.response.CommonDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ScheduleNotFoundException.class)
    public ResponseEntity<CommonDto<Void>> handleScheduleNotFoundException(ScheduleNotFoundException e) {

        return new ResponseEntity<>(new CommonDto<>(HttpStatus.NOT_FOUND, e.getMessage(), null),
                HttpStatus.NOT_FOUND);
    }
}
