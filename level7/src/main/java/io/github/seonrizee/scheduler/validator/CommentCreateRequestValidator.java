package io.github.seonrizee.scheduler.validator;

import io.github.seonrizee.scheduler.dto.request.CommentCreateRequestDto;
import io.github.seonrizee.scheduler.validator.field.ContentsValidator;
import io.github.seonrizee.scheduler.validator.field.PasswordValidator;
import io.github.seonrizee.scheduler.validator.field.UsernameValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 새로운 댓글 생성을 위한 CommentCreateRequestDto의 유효성을 검사합니다. 내용, 작성자, 비밀번호 필드가 비어있지 않은지, 그리고 지정된 길이를 준수하는지 확인합니다.
 */
@Component
@RequiredArgsConstructor
public class CommentCreateRequestValidator implements Validator<CommentCreateRequestDto> {

    private final ContentsValidator contentsValidator;
    private final UsernameValidator usernameValidator;
    private final PasswordValidator passwordValidator;

    @Override
    public void validate(CommentCreateRequestDto dto) {

        final int MAX_CONTENTS_LENGTH = 100;

        contentsValidator.validate(dto.getContents(), MAX_CONTENTS_LENGTH);
        usernameValidator.validate(dto.getUsername());
        passwordValidator.validate(dto.getPassword());
    }
}
