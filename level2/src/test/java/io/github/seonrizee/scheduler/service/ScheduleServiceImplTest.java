package io.github.seonrizee.scheduler.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.seonrizee.scheduler.dto.request.ScheduleRequestDto;
import io.github.seonrizee.scheduler.dto.response.ScheduleResponseDto;
import io.github.seonrizee.scheduler.entity.Schedule;
import io.github.seonrizee.scheduler.repository.ScheduleRepository;
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
    private static final String TEST_USERNAME = "user";
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
}