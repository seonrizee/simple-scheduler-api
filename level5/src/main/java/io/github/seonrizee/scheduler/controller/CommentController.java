package io.github.seonrizee.scheduler.controller;

import io.github.seonrizee.scheduler.dto.request.CommentRequestDto;
import io.github.seonrizee.scheduler.dto.response.CommentResponseDto;
import io.github.seonrizee.scheduler.dto.response.CommonDto;
import io.github.seonrizee.scheduler.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/schedules/{scheduleId}/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping
    public ResponseEntity<CommonDto<CommentResponseDto>> createComment(@RequestBody CommentRequestDto requestDto,
                                                                       @PathVariable Long scheduleId) {

        CommentResponseDto responseDto = commentService.createComment(requestDto, scheduleId);

        return new ResponseEntity<>(new CommonDto<>(HttpStatus.CREATED, "success", responseDto), HttpStatus.CREATED);
    }
}
