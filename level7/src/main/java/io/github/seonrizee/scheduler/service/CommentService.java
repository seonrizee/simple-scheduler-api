package io.github.seonrizee.scheduler.service;

import io.github.seonrizee.scheduler.dto.request.CommentCreateRequestDto;
import io.github.seonrizee.scheduler.dto.response.CommentResponseDto;
import io.github.seonrizee.scheduler.dto.response.CommentsResponseDto;

/**
 * 댓글(Comment) 관련 비즈니스 로직을 처리하는 서비스 인터페이스.
 */
public interface CommentService {

    /**
     * 특정 일정에 새로운 댓글을 생성합니다.
     *
     * @param scheduleId 댓글을 추가할 일정의 ID
     * @param requestDto 댓글 생성 요청 데이터
     * @return 생성된 댓글 정보
     */
    CommentResponseDto createComment(Long scheduleId, CommentCreateRequestDto requestDto);

    /**
     * 특정 일정에 달린 모든 댓글을 조회합니다.
     *
     * @param scheduleId 댓글을 조회할 일정의 ID
     * @return 해당 일정의 댓글 목록을 담은 DTO
     */
    CommentsResponseDto findCommentsByScheduleId(Long scheduleId);
}
