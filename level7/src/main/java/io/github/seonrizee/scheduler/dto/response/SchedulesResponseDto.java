package io.github.seonrizee.scheduler.dto.response;

import java.util.List;
import lombok.Getter;

/**
 * 일정 목록 조회 응답 데이터를 담는 DTO
 */
@Getter
public class SchedulesResponseDto {

    List<ScheduleResponseDto> scheduleResponseDtoList;

    public SchedulesResponseDto(List<ScheduleResponseDto> scheduleResponseDtoList) {
        this.scheduleResponseDtoList = scheduleResponseDtoList;
    }
}
