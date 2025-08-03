package io.github.seonrizee.scheduler.mapper;

import io.github.seonrizee.scheduler.dto.request.ScheduleCreateRequestDto;
import io.github.seonrizee.scheduler.dto.response.CommentsResponseDto;
import io.github.seonrizee.scheduler.dto.response.ScheduleResponseDto;
import io.github.seonrizee.scheduler.dto.response.SchedulesResponseDto;
import io.github.seonrizee.scheduler.entity.Schedule;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class ScheduleMapper {
    
    // RequestDTO -> Entity
    public Schedule toEntity(ScheduleCreateRequestDto requestDto) {
        return Schedule.builder()
                .title(requestDto.getTitle())
                .contents(requestDto.getContents())
                .username(requestDto.getUsername())
                .password(requestDto.getPassword())
                .build();
    }

    // Entity -> ResponseDTO (댓글 목록 미포함)
    public ScheduleResponseDto toResponseDto(Schedule schedule) {
        return new ScheduleResponseDto(
                schedule.getId(),
                schedule.getTitle(),
                schedule.getContents(),
                schedule.getUsername(),
                schedule.getCreatedAt(),
                schedule.getUpdatedAt(),
                null // 댓글 목록은 비워둠
        );
    }

    // Entity + CommentDTOs -> ResponseDTO (댓글 목록 포함)
    public ScheduleResponseDto toResponseDto(Schedule schedule, CommentsResponseDto commentsResponseDto) {

        return new ScheduleResponseDto(
                schedule.getId(),
                schedule.getTitle(),
                schedule.getContents(),
                schedule.getUsername(),
                schedule.getCreatedAt(),
                schedule.getUpdatedAt(),
                commentsResponseDto.getCommentResponseDtoList()
        );
    }

    // List<Entity> -> Wrapper ResponseDTO
    public SchedulesResponseDto toSchedulesResponseDto(List<Schedule> schedules) {
        List<ScheduleResponseDto> scheduleResponseDtoList = schedules.stream()
                .map(this::toResponseDto)
                .toList();
        return new SchedulesResponseDto(scheduleResponseDtoList);
    }
}