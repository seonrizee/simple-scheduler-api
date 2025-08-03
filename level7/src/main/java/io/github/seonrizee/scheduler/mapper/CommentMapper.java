package io.github.seonrizee.scheduler.mapper;

import io.github.seonrizee.scheduler.dto.request.CommentCreateRequestDto;
import io.github.seonrizee.scheduler.dto.response.CommentResponseDto;
import io.github.seonrizee.scheduler.dto.response.CommentsResponseDto;
import io.github.seonrizee.scheduler.entity.Comment;
import java.util.List;
import org.springframework.stereotype.Component;

/**
 * Comment 엔티티와 관련 DTO 간의 데이터 변환을 담당하는 매퍼 클래스.
 */
@Component
public class CommentMapper {

    /**
     * CommentCreateRequestDto를 Comment 엔티티로 변환합니다.
     *
     * @param scheduleId 댓글이 속한 일정의 ID
     * @param requestDto 댓글 생성 요청 DTO
     * @return 변환된 Comment 엔티티
     */
    public Comment toEntity(Long scheduleId, CommentCreateRequestDto requestDto) {

        return Comment.builder()
                .scheduleId(scheduleId)
                .contents(requestDto.getContents())
                .username(requestDto.getUsername())
                .password(requestDto.getPassword())
                .build();
    }

    /**
     * Comment 엔티티를 CommentResponseDto로 변환합니다.
     *
     * @param comment 변환할 Comment 엔티티
     * @return 변환된 CommentResponseDto
     */
    public CommentResponseDto toResponseDto(Comment comment) {
        return new CommentResponseDto(
                comment.getId(),
                comment.getContents(),
                comment.getUsername(),
                comment.getCreatedAt(),
                comment.getUpdatedAt()
        );
    }

    /**
     * Comment 엔티티 목록을 CommentsResponseDto로 변환합니다.
     *
     * @param comments 변환할 Comment 엔티티 목록
     * @return 변환된 CommentsResponseDto
     */
    public CommentsResponseDto toCommentsResponseDto(List<Comment> comments) {
        List<CommentResponseDto> commentResponseDtoList = comments.stream()
                .map(this::toResponseDto)
                .toList();
        return new CommentsResponseDto(commentResponseDtoList);
    }
}