package io.github.seonrizee.scheduler.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 일정 생성을 위한 요청 데이터를 담는 DTO
 */
@Getter
@AllArgsConstructor
public class ScheduleCreateRequestDto {

    private String title;
    private String contents;
    private String username;
    private String password;
}
