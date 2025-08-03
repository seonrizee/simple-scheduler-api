package io.github.seonrizee.scheduler.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 댓글 생성을 위한 요청 데이터를 담는 DTO
 */
@Getter
@AllArgsConstructor
public class CommentCreateRequestDto {

    private String contents;
    private String username;
    private String password;
}
