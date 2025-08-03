package io.github.seonrizee.scheduler.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.github.seonrizee.scheduler.dto.request.CommentRequestDto;
import io.github.seonrizee.scheduler.dto.response.CommentResponseDto;
import io.github.seonrizee.scheduler.entity.Comment;
import io.github.seonrizee.scheduler.entity.Schedule;
import io.github.seonrizee.scheduler.exception.CommentLimitExceededException;
import io.github.seonrizee.scheduler.exception.ScheduleNotFoundException;
import io.github.seonrizee.scheduler.repository.CommentRepository;
import io.github.seonrizee.scheduler.repository.ScheduleRepository;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CommentServiceImplTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private ScheduleRepository scheduleRepository;

    @InjectMocks
    private CommentServiceImpl commentService;

    @Test
    @DisplayName("사용자는 일정의 ID를 이용하여 특정 일정에 댓글을 작성할 수 있다.")
    void createComment_withValidScheduleAndUnderCommentLimit_shouldSucceed() {

        // given
        final Long scheduleId = 1L;
        final String contents = "새로운 댓글입니다.";
        final String username = "user1";
        final String password = "1234";
        final CommentRequestDto requestDto = new CommentRequestDto(contents, username, password);
        final Comment savedComment = new Comment(scheduleId, requestDto);

        // Mocking
        // 1. scheduleRepository가 scheduleId를 찾았다고 가정
        when(scheduleRepository.findById(scheduleId)).thenReturn(Optional.of(new Schedule()));
        // 2. 댓글 수가 10개 미만이라고 가정
        when(commentRepository.countByScheduleId(scheduleId)).thenReturn(5L);
        // 3. 댓글 저장이 성공했다고 가정
        when(commentRepository.save(any(Comment.class))).thenReturn(savedComment);

        // when
        final CommentResponseDto responseDto = commentService.createComment(scheduleId, requestDto);

        // then
        assertThat(responseDto.getContents()).isEqualTo(contents);
        assertThat(responseDto.getUsername()).isEqualTo(username);

        // verify
        verify(scheduleRepository, times(1)).findById(scheduleId);
        verify(commentRepository, times(1)).countByScheduleId(scheduleId);
        verify(commentRepository, times(1)).save(any(Comment.class));
    }

    @Test
    @DisplayName("사용자가 존재하지 않는 일정 ID에 대해 댓글 작성 시 예외가 발생한다.")
    void createComment_withNonExistentSchedule_shouldThrowScheduleNotFoundException() {
        // given
        final Long nonExistentScheduleId = 99L;
        final CommentRequestDto requestDto = new CommentRequestDto("내용", "작성자", "1234");

        // Mocking: scheduleRepository가 비어있는 Optional을 반환한다고 가정
        when(scheduleRepository.findById(nonExistentScheduleId)).thenReturn(Optional.empty());

        // when & then
        assertThatThrownBy(() -> commentService.createComment(nonExistentScheduleId, requestDto))
                .isInstanceOf(ScheduleNotFoundException.class);

        // verify
        verify(scheduleRepository, times(1)).findById(nonExistentScheduleId);
        verify(commentRepository, never()).countByScheduleId(any(Long.class));
        verify(commentRepository, never()).save(any(Comment.class));
    }

    @Test
    @DisplayName("사용자가 선택한 일정의 댓글 개수가 10개를 초과하면 예외가 발생한다.")
    void createComment_whenCommentLimitIsExceeded_shouldThrowCommentLimitExceededException() {
        // given
        final Long scheduleId = 1L;
        final CommentRequestDto requestDto = new CommentRequestDto("내용", "작성자", "1234");

        when(scheduleRepository.findById(scheduleId)).thenReturn(Optional.of(new Schedule()));
        when(commentRepository.countByScheduleId(scheduleId)).thenReturn(10L);

        // when & then
        assertThatThrownBy(() -> commentService.createComment(scheduleId, requestDto))
                .isInstanceOf(CommentLimitExceededException.class);

        verify(scheduleRepository, times(1)).findById(scheduleId);
        verify(commentRepository, times(1)).countByScheduleId(scheduleId);
        verify(commentRepository, never()).save(any(Comment.class));
    }
}