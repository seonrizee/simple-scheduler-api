package io.github.seonrizee.scheduler.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.seonrizee.scheduler.dto.request.ScheduleRequestDto;
import io.github.seonrizee.scheduler.dto.response.ScheduleResponseDto;
import io.github.seonrizee.scheduler.dto.response.SchedulesResponseDto;
import io.github.seonrizee.scheduler.entity.Schedule;
import io.github.seonrizee.scheduler.repository.ScheduleRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ScheduleServiceImplTest {

    private static final String TEST_TITLE = "테스트 제목";
    private static final String TEST_CONTENTS = "테스트 내용";
    private static final String TEST_USERNAME = "작성자4";
    private static final String TEST_PASSWORD = "1234";

    @Mock
    private ScheduleRepository scheduleRepository;

    @InjectMocks
    private ScheduleServiceImpl scheduleService;

    @Test
    @DisplayName("사용자는 일정을 생성할 수 있다.")
    void createSchedule() {
        // given
        ScheduleRequestDto requestDto = new ScheduleRequestDto(TEST_TITLE, TEST_CONTENTS, TEST_USERNAME, TEST_PASSWORD);
        Schedule schedule = new Schedule(requestDto);

        when(scheduleRepository.save(any(Schedule.class))).thenReturn(schedule);

        // when
        ScheduleResponseDto responseDto = scheduleService.createSchedule(requestDto);

        // then
        assertThat(responseDto.getTitle()).isEqualTo(TEST_TITLE);
        assertThat(responseDto.getContents()).isEqualTo(TEST_CONTENTS);
        assertThat(responseDto.getUsername()).isEqualTo(TEST_USERNAME);

        verify(scheduleRepository, times(1)).save(any(Schedule.class));

    }

    @Test
    @DisplayName("사용자는 전체 일정을 조회할 수 있다.")
    void findAllSchedules() {

        // given
        ScheduleRequestDto requestDto1 = new ScheduleRequestDto(TEST_TITLE, TEST_CONTENTS, TEST_USERNAME,
                TEST_PASSWORD);
        ScheduleRequestDto requestDto2 = new ScheduleRequestDto("다른 제목", "다른 내용", "작성자5", "5678");
        Schedule schedule1 = new Schedule(requestDto1);
        Schedule schedule2 = new Schedule(requestDto2);
        List<Schedule> allSchedules = List.of(schedule1, schedule2);

        when(scheduleRepository.findAll()).thenReturn(allSchedules);

        // when
        SchedulesResponseDto responseDto = scheduleService.findAllSchedules(Optional.empty());

        // then
        assertThat(responseDto.getScheduleResponseDtoList()).hasSize(2);
        verify(scheduleRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("사용자는 특정 사용자의 전체 일정을 조회할 수 있다.")
    void findSchedulesByUsername() {

        // given
        ScheduleRequestDto requestDto1 = new ScheduleRequestDto(TEST_TITLE, TEST_CONTENTS, TEST_USERNAME,
                TEST_PASSWORD);
        ScheduleRequestDto requestDto2 = new ScheduleRequestDto("다른 제목", "다른 내용", TEST_USERNAME, "5678");
        Schedule schedule1 = new Schedule(requestDto1);
        Schedule schedule2 = new Schedule(requestDto2);
        List<Schedule> userSchedules = List.of(schedule1, schedule2);

        when(scheduleRepository.findByUsername(TEST_USERNAME)).thenReturn(userSchedules);

        // when
        SchedulesResponseDto responseDto = scheduleService.findAllSchedules(Optional.of(TEST_USERNAME));

        // then
        assertThat(responseDto.getScheduleResponseDtoList()).hasSize(2);
        assertThat(responseDto.getScheduleResponseDtoList())
                .allMatch(dto -> dto.getUsername().equals(TEST_USERNAME));

        verify(scheduleRepository, times(1)).findByUsername(TEST_USERNAME);
        verify(scheduleRepository, never()).findAll();
    }
}