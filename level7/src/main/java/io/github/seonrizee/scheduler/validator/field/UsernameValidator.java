package io.github.seonrizee.scheduler.validator.field;

import org.springframework.stereotype.Component;

@Component
public class UsernameValidator {

    public void validate(String username) {
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("작성자를 입력해주세요.");
        }
    }
}
