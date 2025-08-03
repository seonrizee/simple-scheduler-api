package io.github.seonrizee.scheduler.validator;

import io.github.seonrizee.scheduler.dto.request.CommentCreateRequestDto;
import io.github.seonrizee.scheduler.validator.field.ContentsValidator;
import io.github.seonrizee.scheduler.validator.field.PasswordValidator;
import io.github.seonrizee.scheduler.validator.field.UsernameValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

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
