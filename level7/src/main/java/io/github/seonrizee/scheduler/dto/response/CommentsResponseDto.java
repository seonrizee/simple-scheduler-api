package io.github.seonrizee.scheduler.dto.response;

import java.util.List;
import lombok.Getter;

/**
 * 댓글 목록 조회 응답 데이터를 담는 DTO
 */
@Getter
public class CommentsResponseDto {

    private final List<CommentResponseDto> commentResponseDtoList;

    public CommentsResponseDto(List<CommentResponseDto> commentResponseDtoList) {
        this.commentResponseDtoList = commentResponseDtoList;
    }
}
