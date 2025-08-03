package io.github.seonrizee.scheduler.mapper;

import io.github.seonrizee.scheduler.dto.request.CommentCreateRequestDto;
import io.github.seonrizee.scheduler.dto.response.CommentResponseDto;
import io.github.seonrizee.scheduler.dto.response.CommentsResponseDto;
import io.github.seonrizee.scheduler.entity.Comment;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class CommentMapper {

    // RequestDTO -> Entity
    public Comment toEntity(Long scheduleId, CommentCreateRequestDto requestDto) {

        return Comment.builder()
                .scheduleId(scheduleId)
                .contents(requestDto.getContents())
                .username(requestDto.getUsername())
                .password(requestDto.getPassword())
                .build();
    }

    // Entity -> ResponseDTO
    public CommentResponseDto toResponseDto(Comment comment) {
        return new CommentResponseDto(
                comment.getId(),
                comment.getContents(),
                comment.getUsername(),
                comment.getCreatedAt(),
                comment.getUpdatedAt()
        );
    }

    // List<Entity> -> Wrapper ResponseDTO
    public CommentsResponseDto toCommentsResponseDto(List<Comment> comments) {
        List<CommentResponseDto> commentResponseDtoList = comments.stream()
                .map(this::toResponseDto)
                .toList();
        return new CommentsResponseDto(commentResponseDtoList);
    }
}