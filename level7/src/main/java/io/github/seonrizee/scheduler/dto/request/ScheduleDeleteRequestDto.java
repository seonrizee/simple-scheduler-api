package io.github.seonrizee.scheduler.dto.request;


import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 일정 삭제를 위한 요청 데이터를 담는 DTO
 */
@Getter
@AllArgsConstructor
public class ScheduleDeleteRequestDto {

    private String password;
}
