package io.github.seonrizee.scheduler.validator.field;

import org.springframework.stereotype.Component;

/**
 * 제목 필드의 유효성을 검사하는 클래스.
 */
@Component
public class TitleValidator {
    /**
     * 제목이 비어있지 않고, 지정된 최대 길이를 초과하지 않는지 확인합니다.
     *
     * @param title     검사할 제목 문자열
     * @param maxLength 제목의 최대 허용 길이
     * @throws IllegalArgumentException 제목이 null이거나 비어있거나, 최대 길이를 초과할 경우
     */
    public void validate(String title, int maxLength) {

        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("제목을 입력해주세요.");
        }
        if (title.length() > maxLength) {
            throw new IllegalArgumentException("제목은 " + maxLength + "자 이내로 입력해주세요.");
        }
    }
}