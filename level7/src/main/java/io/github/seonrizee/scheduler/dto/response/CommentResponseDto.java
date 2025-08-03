package io.github.seonrizee.scheduler.dto.response;

import io.github.seonrizee.scheduler.entity.Comment;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentResponseDto {

    private Long id;
    private String contents;
    private String username;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public CommentResponseDto(Comment savedComment) {
        this.id = savedComment.getId();
        this.contents = savedComment.getContents();
        this.username = savedComment.getUsername();
        this.createdAt = savedComment.getCreatedAt();
        this.updatedAt = savedComment.getUpdatedAt();
    }
}
