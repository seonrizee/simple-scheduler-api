package io.github.seonrizee.scheduler.service;

import io.github.seonrizee.scheduler.dto.request.CommentCreateRequestDto;
import io.github.seonrizee.scheduler.dto.response.CommentResponseDto;
import io.github.seonrizee.scheduler.dto.response.CommentsResponseDto;

public interface CommentService {

    CommentResponseDto createComment(Long scheduleId, CommentCreateRequestDto requestDto);

    CommentsResponseDto findCommentsByScheduleId(Long scheduleId);
}
