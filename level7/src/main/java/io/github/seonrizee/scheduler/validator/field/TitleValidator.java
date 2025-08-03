package io.github.seonrizee.scheduler.validator.field;

import org.springframework.stereotype.Component;

@Component
public class TitleValidator {
    public void validate(String title, int maxLength) {

        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("제목을 입력해주세요.");
        }
        if (title.length() > maxLength) {
            throw new IllegalArgumentException("제목은 " + maxLength + "자 이내로 입력해주세요.");
        }
    }
}