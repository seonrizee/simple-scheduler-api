package io.github.seonrizee.scheduler.validator;

import io.github.seonrizee.scheduler.dto.request.ScheduleUpdateRequestDto;
import io.github.seonrizee.scheduler.validator.field.PasswordValidator;
import io.github.seonrizee.scheduler.validator.field.TitleValidator;
import io.github.seonrizee.scheduler.validator.field.UsernameValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

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
