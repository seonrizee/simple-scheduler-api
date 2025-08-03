package io.github.seonrizee.scheduler.validator;

import io.github.seonrizee.scheduler.dto.request.ScheduleDeleteRequestDto;
import io.github.seonrizee.scheduler.validator.field.PasswordValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * 일정 삭제를 위한 ScheduleDeleteRequestDto의 유효성을 검사합니다. 비밀번호 필드가 비어있지 않은지 확인합니다.
 */
@Component
@RequiredArgsConstructor
public class ScheduleDeleteRequestValidator implements Validator<ScheduleDeleteRequestDto> {

    private final PasswordValidator passwordValidator;

    @Override
    public void validate(ScheduleDeleteRequestDto dto) {

        passwordValidator.validate(dto.getPassword());
    }
}
