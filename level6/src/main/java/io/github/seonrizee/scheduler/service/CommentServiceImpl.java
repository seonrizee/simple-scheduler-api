package io.github.seonrizee.scheduler.service;

import io.github.seonrizee.scheduler.dto.request.CommentRequestDto;
import io.github.seonrizee.scheduler.dto.response.CommentResponseDto;
import io.github.seonrizee.scheduler.entity.Comment;
import io.github.seonrizee.scheduler.exception.CommentLimitExceededException;
import io.github.seonrizee.scheduler.repository.CommentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentServiceImpl implements CommentService {

    private static final long MAX_COMMENT_COUNT = 10;

    private final CommentRepository commentRepository;
    private final ScheduleService scheduleService;

    public CommentServiceImpl(CommentRepository commentRepository, ScheduleService scheduleService) {
        this.commentRepository = commentRepository;
        this.scheduleService = scheduleService;
    }

    @Override
    @Transactional
    public CommentResponseDto createComment(Long scheduleId, CommentRequestDto requestDto) {

        scheduleService.findScheduleByIdOrThrow(scheduleId);

        long commentCnt = commentRepository.countByScheduleId(scheduleId);
        if (commentCnt >= MAX_COMMENT_COUNT) {
            throw new CommentLimitExceededException();
        }

        Comment savedComment = commentRepository.save(new Comment(scheduleId, requestDto));

        return new CommentResponseDto(savedComment);
    }
}
