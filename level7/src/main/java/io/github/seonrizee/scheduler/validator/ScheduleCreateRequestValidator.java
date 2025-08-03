package io.github.seonrizee.scheduler.validator;

import io.github.seonrizee.scheduler.dto.request.ScheduleCreateRequestDto;
import io.github.seonrizee.scheduler.validator.field.ContentsValidator;
import io.github.seonrizee.scheduler.validator.field.PasswordValidator;
import io.github.seonrizee.scheduler.validator.field.TitleValidator;
import io.github.seonrizee.scheduler.validator.field.UsernameValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 새로운 일정 생성을 위한 ScheduleCreateRequestDto의 유효성을 검사합니다. 제목, 내용, 작성자, 비밀번호 필드가 비어있지 않은지, 그리고 지정된 길이를 준수하는지 확인합니다.
 */
@Component
@RequiredArgsConstructor
public class ScheduleCreateRequestValidator implements Validator<ScheduleCreateRequestDto> {

    private final TitleValidator titleValidator;
    private final ContentsValidator contentsValidator;
    private final UsernameValidator usernameValidator;
    private final PasswordValidator passwordValidator;

    @Override
    public void validate(ScheduleCreateRequestDto dto) {

        final int MAX_TITLE_LENGTH = 30;
        final int MAX_CONTENTS_LENGTH = 200;

        titleValidator.validate(dto.getTitle(), MAX_TITLE_LENGTH);
        contentsValidator.validate(dto.getContents(), MAX_CONTENTS_LENGTH);
        usernameValidator.validate(dto.getUsername());
        passwordValidator.validate(dto.getPassword());
    }
}
