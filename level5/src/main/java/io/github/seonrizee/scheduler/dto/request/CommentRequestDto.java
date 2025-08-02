package io.github.seonrizee.scheduler.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CommentRequestDto {

    private String contents;
    private String username;
    private String password;
}
