package io.github.seonrizee.scheduler.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public class CommonDto<T> {

    private int statusCode;
    private String message;
    private T data;

    public CommonDto(HttpStatus status, String message, T data) {
        this.statusCode = status.value();
        this.message = message;
        this.data = data;
    }
}
