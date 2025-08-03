package io.github.seonrizee.scheduler.validator;

import io.github.seonrizee.scheduler.dto.request.ScheduleUpdateRequestDto;
import io.github.seonrizee.scheduler.validator.field.PasswordValidator;
import io.github.seonrizee.scheduler.validator.field.TitleValidator;
import io.github.seonrizee.scheduler.validator.field.UsernameValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 일정 수정을 위한 ScheduleUpdateRequestDto의 유효성을 검사합니다. 제목, 작성자, 비밀번호 필드가 비어있지 않은지, 그리고 지정된 길이를 준수하는지 확인합니다.
 */
@Component
@RequiredArgsConstructor
public class ScheduleUpdateRequestValidator implements Validator<ScheduleUpdateRequestDto> {

    private final TitleValidator titleValidator;
    private final UsernameValidator usernameValidator;
    private final PasswordValidator passwordValidator;

    @Override
    public void validate(ScheduleUpdateRequestDto dto) {

        final int MAX_TITLE_LENGTH = 30;

        titleValidator.validate(dto.getTitle(), MAX_TITLE_LENGTH);
        usernameValidator.validate(dto.getUsername());
        passwordValidator.validate(dto.getPassword());
    }
}
