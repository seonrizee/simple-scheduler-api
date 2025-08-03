package io.github.seonrizee.scheduler.validator.field;

import org.springframework.stereotype.Component;

@Component
public class ContentsValidator {
    public void validate(String contents, int maxLength) {

        if (contents == null || contents.isBlank()) {
            throw new IllegalArgumentException("내용을 입력해주세요.");
        }
        if (contents.length() > maxLength) {
            throw new IllegalArgumentException("내용은 " + maxLength + "자 이내로 입력해주세요.");
        }
    }

}
