package io.github.seonrizee.scheduler.service;

import io.github.seonrizee.scheduler.dto.request.PasswordRequestDto;
import io.github.seonrizee.scheduler.dto.request.ScheduleRequestDto;
import io.github.seonrizee.scheduler.dto.request.ScheduleUpdateRequestDto;
import io.github.seonrizee.scheduler.dto.response.ScheduleResponseDto;
import io.github.seonrizee.scheduler.dto.response.SchedulesResponseDto;
import io.github.seonrizee.scheduler.entity.Schedule;
import java.util.Optional;

public interface ScheduleService {

    ScheduleResponseDto createSchedule(ScheduleRequestDto requestDto);

    SchedulesResponseDto findAllSchedules(Optional<String> username);

    ScheduleResponseDto findScheduleById(Long id);

    ScheduleResponseDto updateScheduleById(Long id, ScheduleUpdateRequestDto requestDto);

    void deleteScheduleById(Long id, PasswordRequestDto password);

    Schedule findScheduleByIdOrThrow(Long id);

}
