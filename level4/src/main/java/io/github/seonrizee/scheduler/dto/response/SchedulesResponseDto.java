package io.github.seonrizee.scheduler.dto.response;

import io.github.seonrizee.scheduler.entity.Schedule;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public class SchedulesResponseDto {

    List<ScheduleResponseDto> scheduleResponseDtoList;

    public SchedulesResponseDto(List<Schedule> schedules) {
        scheduleResponseDtoList = schedules.stream()
                .map(ScheduleResponseDto::new)
                .collect(Collectors.toList());
    }
}
