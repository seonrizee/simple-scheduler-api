package io.github.seonrizee.scheduler.validator.field;

import org.springframework.stereotype.Component;

/**
 * 작성자명 필드의 유효성을 검사하는 클래스.
 */
@Component
public class UsernameValidator {

    /**
     * 작성자명이 비어있지 않은지 확인합니다.
     *
     * @param username 검사할 작성자명 문자열
     * @throws IllegalArgumentException 작성자명이 null이거나 비어있을 경우
     */
    public void validate(String username) {
        if (username == null || username.isBlank()) {
            throw new IllegalArgumentException("작성자를 입력해주세요.");
        }
    }
}
