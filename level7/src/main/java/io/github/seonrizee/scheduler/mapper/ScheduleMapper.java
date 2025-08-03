package io.github.seonrizee.scheduler.mapper;

import io.github.seonrizee.scheduler.dto.request.ScheduleCreateRequestDto;
import io.github.seonrizee.scheduler.dto.response.CommentsResponseDto;
import io.github.seonrizee.scheduler.dto.response.ScheduleResponseDto;
import io.github.seonrizee.scheduler.dto.response.SchedulesResponseDto;
import io.github.seonrizee.scheduler.entity.Schedule;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Schedule 엔티티와 관련 DTO 간의 데이터 변환을 담당하는 매퍼 클래스.
 */
@Component
@RequiredArgsConstructor
public class ScheduleMapper {
    
    /**
     * ScheduleCreateRequestDto를 Schedule 엔티티로 변환합니다.
     *
     * @param requestDto 일정 생성 요청 DTO
     * @return 변환된 Schedule 엔티티
     */
    public Schedule toEntity(ScheduleCreateRequestDto requestDto) {
        return Schedule.builder()
                .title(requestDto.getTitle())
                .contents(requestDto.getContents())
                .username(requestDto.getUsername())
                .password(requestDto.getPassword())
                .build();
    }

    /**
     * Schedule 엔티티를 ScheduleResponseDto로 변환합니다. (댓글 미포함)
     *
     * @param schedule 변환할 Schedule 엔티티
     * @return 변환된 ScheduleResponseDto
     */
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

    /**
     * Schedule 엔티티와 댓글 목록 DTO를 ScheduleResponseDto로 변환합니다. (댓글 포함)
     *
     * @param schedule            변환할 Schedule 엔티티
     * @param commentsResponseDto 해당 일정의 댓글 목록을 담은 DTO
     * @return 댓글 목록이 포함된 ScheduleResponseDto
     */
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

    /**
     * Schedule 엔티티 목록을 SchedulesResponseDto로 변환합니다.
     *
     * @param schedules 변환할 Schedule 엔티티 목록
     * @return 변환된 SchedulesResponseDto
     */
    public SchedulesResponseDto toSchedulesResponseDto(List<Schedule> schedules) {
        List<ScheduleResponseDto> scheduleResponseDtoList = schedules.stream()
                .map(this::toResponseDto)
                .toList();
        return new SchedulesResponseDto(scheduleResponseDtoList);
    }
}