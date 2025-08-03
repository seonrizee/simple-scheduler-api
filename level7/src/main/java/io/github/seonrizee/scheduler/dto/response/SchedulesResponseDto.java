package io.github.seonrizee.scheduler.dto.response;

import java.util.List;
import lombok.Getter;

@Getter
public class SchedulesResponseDto {

    List<ScheduleResponseDto> scheduleResponseDtoList;

    public SchedulesResponseDto(List<ScheduleResponseDto> scheduleResponseDtoList) {
        this.scheduleResponseDtoList = scheduleResponseDtoList;
    }
}
