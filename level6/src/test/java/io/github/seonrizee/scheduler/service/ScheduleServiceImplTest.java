package io.github.seonrizee.scheduler.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.seonrizee.scheduler.dto.request.CommentRequestDto;
import io.github.seonrizee.scheduler.dto.request.PasswordRequestDto;
import io.github.seonrizee.scheduler.dto.request.ScheduleRequestDto;
import io.github.seonrizee.scheduler.dto.request.ScheduleUpdateRequestDto;
import io.github.seonrizee.scheduler.dto.response.CommentResponseDto;
import io.github.seonrizee.scheduler.dto.response.CommentsResponseDto;
import io.github.seonrizee.scheduler.dto.response.ScheduleResponseDto;
import io.github.seonrizee.scheduler.dto.response.SchedulesResponseDto;
import io.github.seonrizee.scheduler.entity.Comment;
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

    @Mock
    private CommentService commentService;

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
        final Schedule schedule = new Schedule(requestDto);

        when(scheduleRepository.save(any(Schedule.class))).thenReturn(schedule);

        // when
        final ScheduleResponseDto responseDto = scheduleService.createSchedule(requestDto);

        // then
        assertThat(responseDto.getTitle()).isEqualTo(title);
        assertThat(responseDto.getContents()).isEqualTo(contents);
        assertThat(responseDto.getUsername()).isEqualTo(username);
        assertThat(responseDto.getComments()).isNull(); // 생성 시점에는 댓글이 없음

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
        assertThat(responseDto.getScheduleResponseDtoList().get(0).getComments()).isNull(); // 목록 조회 시에는 댓글을 포함하지 않음

        verify(scheduleRepository, times(1)).findAllByOrderByUpdatedAtDesc();
    }

    @Test
    @DisplayName("사용자는 특정 사용자의 전체 일정을 조회할 수 있다.")
    void findScheduleById_withExistingIdAndNoComments_shouldSucceed() {
        // given
        final Long existentId = 1L;
        final Schedule schedule = new Schedule(
                new ScheduleRequestDto("일정 제목", "일정 내용", "user", "pass"));

        // Mocking: 댓글이 없는 상황을 가정
        when(scheduleRepository.findById(existentId)).thenReturn(Optional.of(schedule));
        when(commentService.findCommentsByScheduleId(existentId)).thenReturn(
                new CommentsResponseDto(List.of())); // 빈 댓글 목록 DTO 반환

        // when
        final ScheduleResponseDto responseDto = scheduleService.findScheduleById(existentId);

        // then
        // 1. 일정 정보 검증
        assertThat(responseDto.getTitle()).isEqualTo("일정 제목");
        // 2. 댓글 목록이 비어있는지 검증
        assertThat(responseDto.getComments()).isNotNull().isEmpty();

        // 3. Repository 및 Service 메서드 호출 횟수 검증
        verify(scheduleRepository, times(1)).findById(existentId);
        verify(commentService, times(1)).findCommentsByScheduleId(existentId);
    }

    @Test
    @DisplayName("사용자는 ID로 특정 일정과 일정에 달린 댓글 목록을 조회할 수 있다.")
    void findScheduleById_withExistingIdAndComments_shouldSucceed() {
        // given
        final Long existentId = 1L;
        final Schedule schedule = new Schedule(
                new ScheduleRequestDto("일정 제목", "일정 내용", "user", "pass"));

        // 댓글 목록 준비
        final List<CommentResponseDto> commentDtos = List.of(
                new CommentResponseDto(new Comment(existentId, new CommentRequestDto("댓글 1", "c1", "p1"))),
                new CommentResponseDto(new Comment(existentId, new CommentRequestDto("댓글 2", "c2", "p2")))
        );
        final CommentsResponseDto commentsResponseDto = new CommentsResponseDto(commentDtos);

        // Mocking
        when(scheduleRepository.findById(existentId)).thenReturn(Optional.of(schedule));
        when(commentService.findCommentsByScheduleId(existentId)).thenReturn(commentsResponseDto);

        // when
        final ScheduleResponseDto responseDto = scheduleService.findScheduleById(existentId);

        // then
        // 1. 일정 정보 검증
        assertThat(responseDto.getTitle()).isEqualTo("일정 제목");
        // 2. 댓글 목록 검증
        assertThat(responseDto.getComments()).isNotNull().hasSize(2);
        assertThat(responseDto.getComments().get(0).getContents()).isEqualTo("댓글 1");
        assertThat(responseDto.getComments().get(1).getUsername()).isEqualTo("c2");

        // 3. Repository 및 Service 메서드 호출 횟수 검증
        verify(scheduleRepository, times(1)).findById(existentId);
        verify(commentService, times(1)).findCommentsByScheduleId(existentId);
    }

    @Test
    @DisplayName("사용자가 존재하지 않는 ID로 조회 시 예외가 발생한다.")
    void findScheduleById_withNonExistentId_shouldThrowScheduleNotFoundException() {
        // given
        final Long nonExistentId = 99L;
        when(scheduleRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> scheduleService.findScheduleById(nonExistentId))
                .isInstanceOf(ScheduleNotFoundException.class);

        // 일정 조회가 실패했으므로, 댓글 조회 로직은 호출되면 안 됨
        verify(scheduleRepository, times(1)).findById(nonExistentId);
        verify(commentService, never()).findCommentsByScheduleId(any(Long.class));
    }

    @Test
    @DisplayName("사용자는 선택한 일정에 대해 입력한 비밀번호가 일치하면 일정을 수정할 수 있다.")
    void updateScheduleById_withValidIdAndPassword_shouldSucceed() {
        // given
        final Long existentId = 1L;
        final String password = "1234";
        final String originalContents = "원본 내용";
        final String updatedTitle = "수정된 제목";
        final String updatedUsername = "수정된 작성자";

        final Schedule existingSchedule = new Schedule(
                new ScheduleRequestDto("원본 제목", originalContents, "user1", password));
        final ScheduleUpdateRequestDto requestDto = new ScheduleUpdateRequestDto(updatedTitle,
                updatedUsername,
                password);

        when(scheduleRepository.findById(existentId)).thenReturn(Optional.of(existingSchedule));
        when(scheduleRepository.saveAndFlush(any(Schedule.class))).thenAnswer(
                invocation -> invocation.getArgument(0));

        // when
        final ScheduleResponseDto responseDto = scheduleService.updateScheduleById(existentId,
                requestDto);

        // then
        assertThat(responseDto.getTitle()).isEqualTo(updatedTitle);
        assertThat(responseDto.getUsername()).isEqualTo(updatedUsername);
        assertThat(responseDto.getContents()).isEqualTo(originalContents);
        assertThat(responseDto.getComments()).isNull(); // 수정 시에는 댓글을 조회하지 않음

        verify(scheduleRepository, times(1)).findById(existentId);
        verify(scheduleRepository, times(1)).saveAndFlush(any(Schedule.class));
    }

    @Test
    @DisplayName("일정 수정 시 선택한 일정의 ID가 존재하지 않으면 예외가 발생한다.")
    void updateScheduleById_withNonExistentId_shouldThrowScheduleNotFoundException() {
        // given
        final Long nonExistentId = 99L;
        final ScheduleUpdateRequestDto requestDto = new ScheduleUpdateRequestDto("title", "user", "pass");
        when(scheduleRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> scheduleService.updateScheduleById(nonExistentId, requestDto))
                .isInstanceOf(ScheduleNotFoundException.class);

        verify(scheduleRepository, times(1)).findById(nonExistentId);
        verify(scheduleRepository, never()).saveAndFlush(any(Schedule.class));
    }

    @Test
    @DisplayName("일정 수정 시 비밀번호가 일치하지 않으면 예외가 발생한다.")
    void updateScheduleById_withIncorrectPassword_shouldThrowInvalidPasswordException() {
        // given
        final Long existentId = 1L;
        final String correctPassword = "password123";
        final String incorrectPassword = "wrong_password";

        final Schedule existingSchedule = new Schedule(
                new ScheduleRequestDto("Original Title", "Contents", "user1", correctPassword));
        final ScheduleUpdateRequestDto requestDto = new ScheduleUpdateRequestDto("Updated Title",
                "Updated User", incorrectPassword);

        when(scheduleRepository.findById(existentId)).thenReturn(Optional.of(existingSchedule));

        // when & then
        assertThatThrownBy(() -> scheduleService.updateScheduleById(existentId, requestDto))
                .isInstanceOf(InvalidPasswordException.class);

        verify(scheduleRepository, times(1)).findById(existentId);
        verify(scheduleRepository, never()).saveAndFlush(any(Schedule.class));
    }

    @Test
    @DisplayName("사용자는 선택한 일정에 대해 입력한 비밀번호가 일치하면 일정을 삭제할 수 있다.")
    void deleteScheduleById_withValidIdAndPassword_shouldSucceed() {
        // given
        final Long existentId = 1L;
        final String correctPassword = "password123";
        final PasswordRequestDto requestDto = new PasswordRequestDto(correctPassword);
        final Schedule existingSchedule = new Schedule(
                new ScheduleRequestDto("Title", "Contents", "user", correctPassword));

        when(scheduleRepository.findById(existentId)).thenReturn(Optional.of(existingSchedule));

        // when
        scheduleService.deleteScheduleById(existentId, requestDto);

        // then
        verify(scheduleRepository, times(1)).findById(existentId);
        verify(scheduleRepository, times(1)).deleteById(existentId);
    }

    @Test
    @DisplayName("일정 삭제 시 ID가 존재하지 않으면 예외가 발생한다.")
    void deleteScheduleById_withNonExistentId_shouldThrowScheduleNotFoundException() {
        // given
        final Long nonExistentId = 99L;
        final PasswordRequestDto requestDto = new PasswordRequestDto("password123");

        when(scheduleRepository.findById(nonExistentId)).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> scheduleService.deleteScheduleById(nonExistentId, requestDto))
                .isInstanceOf(ScheduleNotFoundException.class);

        verify(scheduleRepository, times(1)).findById(nonExistentId);
        verify(scheduleRepository, never()).deleteById(any(Long.class));
    }

    @Test
    @DisplayName("일정 삭제 시 비밀번호가 일치하지 않으면 예외가 발생한다.")
    void deleteScheduleById_withIncorrectPassword_shouldThrowInvalidPasswordException() {
        // given
        final Long existentId = 1L;
        final String correctPassword = "password123";
        final String incorrectPassword = "wrong_password";
        final PasswordRequestDto requestDto = new PasswordRequestDto(incorrectPassword);
        final Schedule existingSchedule = new Schedule(
                new ScheduleRequestDto("Title", "Contents", "user", correctPassword));

        when(scheduleRepository.findById(existentId)).thenReturn(Optional.of(existingSchedule));

        // when & then
        assertThatThrownBy(() -> scheduleService.deleteScheduleById(existentId, requestDto))
                .isInstanceOf(InvalidPasswordException.class);

        verify(scheduleRepository, times(1)).findById(existentId);
        verify(scheduleRepository, never()).deleteById(any(Long.class));
    }
}