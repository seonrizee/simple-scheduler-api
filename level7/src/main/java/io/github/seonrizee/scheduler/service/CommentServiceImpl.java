package io.github.seonrizee.scheduler.service;

import io.github.seonrizee.scheduler.dto.request.CommentRequestDto;
import io.github.seonrizee.scheduler.dto.response.CommentResponseDto;
import io.github.seonrizee.scheduler.dto.response.CommentsResponseDto;
import io.github.seonrizee.scheduler.entity.Comment;
import io.github.seonrizee.scheduler.exception.CommentLimitExceededException;
import io.github.seonrizee.scheduler.exception.ScheduleNotFoundException;
import io.github.seonrizee.scheduler.repository.CommentRepository;
import io.github.seonrizee.scheduler.repository.ScheduleRepository;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentServiceImpl implements CommentService {

    private static final long MAX_COMMENT_COUNT = 10;

    private final CommentRepository commentRepository;
    private final ScheduleRepository scheduleRepository;

    public CommentServiceImpl(CommentRepository commentRepository, ScheduleRepository scheduleRepository) {
        this.commentRepository = commentRepository;
        this.scheduleRepository = scheduleRepository;
    }

    @Override
    @Transactional
    public CommentResponseDto createComment(Long scheduleId, CommentRequestDto requestDto) {

        scheduleRepository.findById(scheduleId)
                .orElseThrow(ScheduleNotFoundException::new);

        long commentCnt = commentRepository.countByScheduleId(scheduleId);
        if (commentCnt >= MAX_COMMENT_COUNT) {
            throw new CommentLimitExceededException();
        }

        Comment savedComment = commentRepository.save(new Comment(scheduleId, requestDto));

        return new CommentResponseDto(savedComment);
    }

    @Override
    @Transactional(readOnly = true)
    public CommentsResponseDto findCommentsByScheduleId(Long scheduleId) {

        List<Comment> comments = commentRepository.findAllByScheduleId(scheduleId);

        return new CommentsResponseDto(
                comments.stream()
                        .map(CommentResponseDto::new)
                        .toList());
    }
}
