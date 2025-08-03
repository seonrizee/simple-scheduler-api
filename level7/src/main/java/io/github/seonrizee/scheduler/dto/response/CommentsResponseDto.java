package io.github.seonrizee.scheduler.dto.response;

import java.util.List;
import lombok.Getter;

@Getter
public class CommentsResponseDto {

    private final List<CommentResponseDto> commentResponseDtoList;

    public CommentsResponseDto(List<CommentResponseDto> commentResponseDtoList) {
        this.commentResponseDtoList = commentResponseDtoList;
    }
}
