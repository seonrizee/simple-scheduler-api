package io.github.seonrizee.scheduler.validator.field;

import org.springframework.stereotype.Component;

@Component
public class PasswordValidator {
    public void validate(String password) {
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("비밀번호를 입력해주세요.");
        }
    }
}