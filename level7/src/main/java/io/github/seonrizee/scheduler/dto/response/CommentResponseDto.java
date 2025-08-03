package io.github.seonrizee.scheduler.dto.response;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 단일 댓글 조회 응답 데이터를 담는 DTO
 */
@Getter
@AllArgsConstructor
public class CommentResponseDto {

    private Long id;
    private String contents;
    private String username;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
