package io.github.seonrizee.scheduler.controller;

import io.github.seonrizee.scheduler.dto.request.ScheduleCreateRequestDto;
import io.github.seonrizee.scheduler.dto.request.ScheduleDeleteRequestDto;
import io.github.seonrizee.scheduler.dto.request.ScheduleUpdateRequestDto;
import io.github.seonrizee.scheduler.dto.response.ApiResponse;
import io.github.seonrizee.scheduler.dto.response.ScheduleResponseDto;
import io.github.seonrizee.scheduler.dto.response.SchedulesResponseDto;
import io.github.seonrizee.scheduler.service.ScheduleService;
import io.github.seonrizee.scheduler.validator.Validator;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 일정(Schedule) 관련 HTTP 요청을 처리하는 컨트롤러
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;
    private final Validator<ScheduleCreateRequestDto> scheduleCreateRequestDtoValidator;
    private final Validator<ScheduleUpdateRequestDto> scheduleUpdateRequestDtoValidator;
    private final Validator<ScheduleDeleteRequestDto> scheduleDeleteRequestDtoValidator;

    /**
     * 새로운 일정을 생성합니다.
     *
     * @param requestDto 일정 생성에 필요한 데이터 (제목, 내용, 작성자, 비밀번호)
     * @return 생성된 일정 정보를 담은 ResponseEntity
     */
    @PostMapping
    public ResponseEntity<ApiResponse<ScheduleResponseDto>> createSchedule(
            @RequestBody ScheduleCreateRequestDto requestDto) {

        scheduleCreateRequestDtoValidator.validate(requestDto);

        ScheduleResponseDto responseDto = scheduleService.createSchedule(requestDto);

        return ApiResponse.created(responseDto);
    }

    /**
     * 전체 일정 목록을 조회합니다.
     * username 파라미터가 제공될 경우, 해당 사용자의 일정만 필터링하여 조회합니다.
     *
     * @param username (선택) 조회할 특정 사용자의 이름
     * @return 일정 목록을 담은 ResponseEntity
     */
    @GetMapping
    public ResponseEntity<ApiResponse<SchedulesResponseDto>> getSchedules(
            @RequestParam(required = false) Optional<String> username) {

        SchedulesResponseDto responseDto = scheduleService.findAllSchedules(username);

        return ApiResponse.ok(responseDto);
    }

    /**
     * ID를 이용하여 특정 일정을 조회합니다.
     *
     * @param id 조회할 일정의 ID
     * @return 특정 일정 정보를 담은 ResponseEntity
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ScheduleResponseDto>> getSchedule(@PathVariable Long id) {

        ScheduleResponseDto responseDto = scheduleService.findScheduleById(id);

        return ApiResponse.ok(responseDto);
    }

    /**
     * 특정 일정을 수정합니다.
     *
     * @param id         수정할 일정의 ID
     * @param requestDto 일정 수정에 필요한 데이터 (제목, 작성자, 비밀번호)
     * @return 수정된 일정 정보를 담은 ResponseEntity
     */
    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<ScheduleResponseDto>> updateSchedule(
            @PathVariable Long id,
            @RequestBody ScheduleUpdateRequestDto requestDto
    ) {

        scheduleUpdateRequestDtoValidator.validate(requestDto);
        ScheduleResponseDto responseDto = scheduleService.updateScheduleById(id, requestDto);

        return ApiResponse.ok(responseDto);
    }

    /**
     * 특정 일정을 삭제합니다.
     *
     * @param id         삭제할 일정의 ID
     * @param requestDto 비밀번호 확인을 위한 데이터
     * @return 작업 성공을 나타내는 ResponseEntity
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteSchedule(
            @PathVariable Long id,
            @RequestBody ScheduleDeleteRequestDto requestDto
    ) {

        scheduleDeleteRequestDtoValidator.validate(requestDto);
        scheduleService.deleteScheduleById(id, requestDto);

        return ApiResponse.ok();
    }
}
