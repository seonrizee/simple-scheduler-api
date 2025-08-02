package io.github.seonrizee.scheduler.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.seonrizee.scheduler.dto.request.ScheduleRequestDto;
import io.github.seonrizee.scheduler.dto.request.ScheduleUpdateRequestDto;
import io.github.seonrizee.scheduler.dto.response.ScheduleResponseDto;
import io.github.seonrizee.scheduler.dto.response.SchedulesResponseDto;
import io.github.seonrizee.scheduler.entity.Schedule;
import io.github.seonrizee.scheduler.exception.InvalidPasswordException;
import io.github.seonrizee.scheduler.exception.ScheduleNotFoundException;
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

    @Mock
    private ScheduleRepository scheduleRepository;

    @InjectMocks
    private ScheduleServiceImpl scheduleService;

    @Test
    @DisplayName("사용자는 일정을 생성할 수 있다.")
    void createSchedule_withValidRequest_shouldSucceed() {
        // given
        final String title = "테스트 제목";
        final String contents = "테스트 내용";
        final String username = "user";
        final String password = "1234";
        final ScheduleRequestDto requestDto = new ScheduleRequestDto(title, contents, username, password);
        Schedule schedule = new Schedule(requestDto);

        when(scheduleRepository.save(any(Schedule.class))).thenReturn(schedule);

        // when
        final ScheduleResponseDto responseDto = scheduleService.createSchedule(requestDto);

        // then
        assertThat(responseDto.getTitle()).isEqualTo(title);
        assertThat(responseDto.getContents()).isEqualTo(contents);
        assertThat(responseDto.getUsername()).isEqualTo(username);

        verify(scheduleRepository, times(1)).save(any(Schedule.class));

    }

    @Test
    @DisplayName("사용자는 전체 일정을 조회할 수 있다.")
    void findAllSchedules_withoutUsername_shouldReturnAllSchedules() {

        // given
        final Schedule schedule1 = new Schedule(
            new ScheduleRequestDto("테스트 제목 1", "테스트 내용 1", "user1", "1111"));
        final Schedule schedule2 = new Schedule(
            new ScheduleRequestDto("테스트 제목 2", "테스트 내용 2", "user2", "2222"));
        final List<Schedule> allSchedules = List.of(schedule1, schedule2);

        when(scheduleRepository.findAllByOrderByUpdatedAtDesc()).thenReturn(allSchedules);

        // when
        final SchedulesResponseDto responseDto = scheduleService.findAllSchedules(Optional.empty());

        // then
        assertThat(responseDto.getScheduleResponseDtoList()).hasSize(2);
        verify(scheduleRepository, times(1)).findAllByOrderByUpdatedAtDesc();
    }

    @Test
    @DisplayName("사용자는 특정 사용자의 전체 일정을 조회할 수 있다.")
    void findAllSchedules_withUsername_shouldReturnSchedulesForThatUser() {

        // given
        final String targetUsername = "targetUser";
        final Schedule schedule1 = new Schedule(
            new ScheduleRequestDto("테스트 제목 1", "테스트 내용 1", targetUsername, "1111"));
        final Schedule schedule2 = new Schedule(
            new ScheduleRequestDto("테스트 제목 2", "테스트 내용 2", targetUsername, "2222"));
        final List<Schedule> userSchedules = List.of(schedule1, schedule2);

        when(scheduleRepository.findByUsernameOrderByUpdatedAtDesc(targetUsername)).thenReturn(userSchedules);

        // when
        final SchedulesResponseDto responseDto = scheduleService.findAllSchedules(Optional.of(targetUsername));

        // then
        assertThat(responseDto.getScheduleResponseDtoList()).hasSize(2);
        assertThat(responseDto.getScheduleResponseDtoList())
                .allMatch(dto -> dto.getUsername().equals(targetUsername));

        verify(scheduleRepository, times(1)).findByUsernameOrderByUpdatedAtDesc(targetUsername);
        verify(scheduleRepository, never()).findAll();
    }

    @Test
    @DisplayName("사용자는 ID로 특정 일정을 조회할 수 있다.")
    void findById_withExistingId_shouldReturnSchedule() {

        // given
        final Long existentId = 1L;
        final String title = "테스트 제목";
        final String contents = "테스트 내용";
        final String username = "user";
        final String password = "1234";
        final Schedule foundSchedule = new Schedule(
            new ScheduleRequestDto(title, contents, username, password));
        when(scheduleRepository.findById(existentId)).thenReturn(Optional.of(foundSchedule));

        // when
        final ScheduleResponseDto responseDto = scheduleService.findById(existentId);

        // then
        assertThat(responseDto).isNotNull();
        assertThat(responseDto.getTitle()).isEqualTo(title);
        assertThat(responseDto.getContents()).isEqualTo(contents);
        assertThat(responseDto.getUsername()).isEqualTo(username);

        verify(scheduleRepository, times(1)).findById(existentId);
    }

    @Test
    @DisplayName("사용자가 존재하지 않는 ID로 조회 시 ScheduleNotFoundException 예외가 발생한다.")
    void findById_withNonExistentId_shouldThrowScheduleNotFoundException() {

        // given
        final Long nonExistentId = 99L;
        when(scheduleRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> scheduleService.findById(nonExistentId))
                .isInstanceOf(ScheduleNotFoundException.class) // 예외 타입 검증
                .hasMessage("선택한 일정이 존재하지 않습니다."); // 예외 메시지 검증

        verify(scheduleRepository, times(1)).findById(nonExistentId);
    }

    @Test
    @DisplayName("사용자가 선택한 일정의 ID와 비밀번호가 일치하면 선택한 ID의 일정을 수정할 수 있다.")
    void updateSchedule_withValidIdAndPassword_shouldSucceed() {
        // given
        final Long existentId = 1L;
        final String password = "1234";
        final String originalContents = "원본 내용";
        final String updatedTitle = "수정된 제목";
        final String updatedUsername = "수정된 작성자";

        final Schedule existingSchedule = new Schedule(
            new ScheduleRequestDto("원본 제목", originalContents, "user1", password));
        final ScheduleUpdateRequestDto requestDto = new ScheduleUpdateRequestDto(updatedTitle, updatedUsername,
            password);

        // Mocking
        when(scheduleRepository.findById(existentId)).thenReturn(Optional.of(existingSchedule));
        when(scheduleRepository.saveAndFlush(any(Schedule.class))).thenAnswer(
            invocation -> invocation.getArgument(0));

        // when
        final ScheduleResponseDto responseDto = scheduleService.updateSchedule(existentId, requestDto);

        // then
        assertThat(responseDto.getTitle()).isEqualTo(updatedTitle);
        assertThat(responseDto.getUsername()).isEqualTo(updatedUsername);
        assertThat(responseDto.getContents()).isEqualTo(originalContents);

        verify(scheduleRepository, times(1)).findById(existentId);
        verify(scheduleRepository, times(1)).saveAndFlush(any(Schedule.class));
    }

    @Test
    @DisplayName("일정 수정 시 선택한 일정의 ID가 존재하지 않으면 예외가 발생한다.")
    void updateSchedule_withNonExistentId_shouldThrowScheduleNotFoundException() {
        // given
        final Long nonExistentId = 99L;
        final ScheduleUpdateRequestDto requestDto = new ScheduleUpdateRequestDto("제목", "작성자", "1234");

        // Mocking: findById가 비어있는 Optional을 반환하도록 설정
        when(scheduleRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        // when & then
        // ScheduleNotFoundException이 발생하는지 검증
        assertThatThrownBy(() -> scheduleService.updateSchedule(nonExistentId, requestDto))
                .isInstanceOf(ScheduleNotFoundException.class);

        // Repository 메서드 호출 횟수 검증 (saveAndFlush는 호출되면 안 됨)
        verify(scheduleRepository, times(1)).findById(nonExistentId);
        verify(scheduleRepository, never()).saveAndFlush(any(Schedule.class));
    }

    @Test
    @DisplayName("일정 수정 시 비밀번호가 일치하지 않으면 예외가 발생한다.")
    void updateSchedule_withIncorrectPassword_shouldThrowInvalidPasswordException() {
        // given
        final Long existentId = 1L;
        final String correctPassword = "1234";
        final String incorrectPassword = "incorrect_password";

        final Schedule existingSchedule = new Schedule(
            new ScheduleRequestDto("원본 제목", "원본 내용", "user1", correctPassword));
        final ScheduleUpdateRequestDto requestDto = new ScheduleUpdateRequestDto("수정된 제목", "수정된 작성자",
                "incorrect_password");

        when(scheduleRepository.findById(existentId)).thenReturn(Optional.of(existingSchedule));

        // when & then
        assertThatThrownBy(() -> scheduleService.updateSchedule(existentId, requestDto))
                .isInstanceOf(InvalidPasswordException.class)
                .hasMessage("비밀번호가 일치하지 않습니다.");

        // Repository 메서드 호출 횟수 검증 (saveAndFlush는 호출되면 안 됨)
        verify(scheduleRepository, times(1)).findById(existentId);
        verify(scheduleRepository, never()).saveAndFlush(any(Schedule.class));
    }
}