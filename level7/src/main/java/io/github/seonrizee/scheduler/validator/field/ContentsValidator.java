package io.github.seonrizee.scheduler.validator.field;

import org.springframework.stereotype.Component;

/**
 * 내용 필드의 유효성을 검사하는 클래스.
 */
@Component
public class ContentsValidator {
    /**
     * 내용이 비어있지 않고, 지정된 최대 길이를 초과하지 않는지 확인합니다.
     *
     * @param contents  검사할 내용 문자열
     * @param maxLength 내용의 최대 허용 길이
     * @throws IllegalArgumentException 내용이 null이거나 비어있거나, 최대 길이를 초과할 경우
     */
    public void validate(String contents, int maxLength) {

        if (contents == null || contents.isBlank()) {
            throw new IllegalArgumentException("내용을 입력해주세요.");
        }
        if (contents.length() > maxLength) {
            throw new IllegalArgumentException("내용은 " + maxLength + "자 이내로 입력해주세요.");
        }
    }

}
