package io.github.seonrizee.scheduler.dto.response;

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
}
