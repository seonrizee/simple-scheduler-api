package io.github.seonrizee.scheduler.service;

import io.github.seonrizee.scheduler.dto.request.ScheduleRequestDto;
import io.github.seonrizee.scheduler.dto.response.ScheduleResponseDto;
import io.github.seonrizee.scheduler.dto.response.SchedulesResponseDto;
import java.util.Optional;

public interface ScheduleService {

    ScheduleResponseDto createSchedule(ScheduleRequestDto requestDto);

    SchedulesResponseDto findAllSchedules(Optional<String> username);
}
