package io.github.seonrizee.scheduler.service;

import io.github.seonrizee.scheduler.dto.request.ScheduleCreateRequestDto;
import io.github.seonrizee.scheduler.dto.request.ScheduleDeleteRequestDto;
import io.github.seonrizee.scheduler.dto.request.ScheduleUpdateRequestDto;
import io.github.seonrizee.scheduler.dto.response.ScheduleResponseDto;
import io.github.seonrizee.scheduler.dto.response.SchedulesResponseDto;
import io.github.seonrizee.scheduler.entity.Schedule;
import java.util.Optional;

public interface ScheduleService {

    ScheduleResponseDto createSchedule(ScheduleCreateRequestDto requestDto);

    SchedulesResponseDto findAllSchedules(Optional<String> username);

    ScheduleResponseDto findScheduleById(Long id);

    ScheduleResponseDto updateScheduleById(Long id, ScheduleUpdateRequestDto requestDto);

    void deleteScheduleById(Long id, ScheduleDeleteRequestDto password);

    Schedule findScheduleByIdOrThrow(Long id);

}
