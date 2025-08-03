package io.github.seonrizee.scheduler.validator.field;

import org.springframework.stereotype.Component;

/**
 * 비밀번호 필드의 유효성을 검사하는 클래스.
 */
@Component
public class PasswordValidator {
    /**
     * 비밀번호가 비어있지 않은지 확인합니다. 비밀번호 일치여부는 Service에서 판단합니다.
     *
     * @param password 검사할 비밀번호 문자열
     * @throws IllegalArgumentException 비밀번호가 null이거나 비어있을 경우
     */
    public void validate(String password) {
        if (password == null || password.isBlank()) {
            throw new IllegalArgumentException("비밀번호를 입력해주세요.");
        }
    }
}