package io.github.seonrizee.scheduler.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ScheduleUpdateRequestDto {

    private String title;
    private String username;
    private String password;
}
