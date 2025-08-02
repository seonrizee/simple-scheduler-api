package io.github.seonrizee.scheduler.service;

import io.github.seonrizee.scheduler.dto.request.CommentRequestDto;
import io.github.seonrizee.scheduler.dto.response.CommentResponseDto;

public interface CommentService {

    CommentResponseDto createComment(Long scheduleId, CommentRequestDto requestDto);
}
