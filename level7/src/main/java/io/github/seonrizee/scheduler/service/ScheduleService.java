package io.github.seonrizee.scheduler.service;

import io.github.seonrizee.scheduler.dto.request.ScheduleCreateRequestDto;
import io.github.seonrizee.scheduler.dto.request.ScheduleDeleteRequestDto;
import io.github.seonrizee.scheduler.dto.request.ScheduleUpdateRequestDto;
import io.github.seonrizee.scheduler.dto.response.ScheduleResponseDto;
import io.github.seonrizee.scheduler.dto.response.SchedulesResponseDto;
import io.github.seonrizee.scheduler.entity.Schedule;
import java.util.Optional;

/**
 * 일정(Schedule) 관련 비즈니스 로직을 처리하는 서비스 인터페이스.
 */
public interface ScheduleService {

    /**
     * 새로운 일정을 생성합니다.
     *
     * @param requestDto 일정 생성 요청 데이터
     * @return 생성된 일정 정보
     */
    ScheduleResponseDto createSchedule(ScheduleCreateRequestDto requestDto);

    /**
     * 모든 일정을 조회합니다. 특정 사용자의 일정만 필터링할 수 있습니다.
     *
     * @param username (선택) 필터링할 사용자의 이름
     * @return 일정 목록 정보
     */
    SchedulesResponseDto findAllSchedules(Optional<String> username);

    /**
     * ID를 이용하여 특정 일정을 조회합니다.
     * 해당 일정에 달린 댓글 목록을 함께 반환합니다.
     *
     * @param id 조회할 일정의 ID
     * @return 조회된 일정 정보 (댓글 포함)
     */
    ScheduleResponseDto findScheduleById(Long id);

    /**
     * ID를 이용하여 특정 일정을 수정합니다.
     *
     * @param id         수정할 일정의 ID
     * @param requestDto 일정 수정 요청 데이터
     * @return 수정된 일정 정보
     */
    ScheduleResponseDto updateScheduleById(Long id, ScheduleUpdateRequestDto requestDto);

    /**
     * ID를 이용하여 특정 일정을 삭제합니다.
     *
     * @param id       삭제할 일정의 ID
     * @param requestDto 비밀번호 확인을 위한 데이터
     */
    void deleteScheduleById(Long id, ScheduleDeleteRequestDto requestDto);

    /**
     * ID에 해당하는 일정을 조회하고, 존재하지 않을 경우 예외를 발생시킵니다.
     * 서비스 내부에서 일정의 존재 여부를 확실히 보장해야 할 때 사용됩니다.
     *
     * @param id 조회할 일정의 ID
     * @return 조회된 Schedule 엔티티
     * @throws io.github.seonrizee.scheduler.exception.ScheduleNotFoundException 해당 ID의 일정이 존재하지 않을 경우
     */
    Schedule findScheduleByIdOrThrow(Long id);

}
