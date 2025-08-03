package io.github.seonrizee.scheduler.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ScheduleResponseDto {

    private Long id;
    private String title;
    private String contents;
    private String username;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<CommentResponseDto> comments;
}
