package io.github.seonrizee.scheduler.validator;

import io.github.seonrizee.scheduler.dto.request.ScheduleDeleteRequestDto;
import io.github.seonrizee.scheduler.validator.field.PasswordValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ScheduleDeleteRequestValidator implements Validator<ScheduleDeleteRequestDto> {

    private final PasswordValidator passwordValidator;

    @Override
    public void validate(ScheduleDeleteRequestDto dto) {

        passwordValidator.validate(dto.getPassword());
    }
}
