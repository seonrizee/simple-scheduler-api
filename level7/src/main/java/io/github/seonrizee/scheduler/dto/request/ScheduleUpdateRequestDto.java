package io.github.seonrizee.scheduler.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 일정 수정을 위한 요청 데이터를 담는 DTO
 */
@Getter
@AllArgsConstructor
public class ScheduleUpdateRequestDto {

    private String title;
    private String username;
    private String password;
}
