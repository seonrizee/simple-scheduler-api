package io.github.seonrizee.scheduler.controller;

import io.github.seonrizee.scheduler.dto.request.CommentCreateRequestDto;
import io.github.seonrizee.scheduler.dto.response.CommentResponseDto;
import io.github.seonrizee.scheduler.dto.response.CommonDto;
import io.github.seonrizee.scheduler.service.CommentService;
import io.github.seonrizee.scheduler.validator.CommentCreateRequestValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/schedules/{scheduleId}/comments")
public class CommentController {

    private final CommentService commentService;
    private final CommentCreateRequestValidator commentCreateRequestValidator;

    @PostMapping
    public ResponseEntity<CommonDto<CommentResponseDto>> createComment(@RequestBody CommentCreateRequestDto requestDto,
                                                                       @PathVariable Long scheduleId) {

        commentCreateRequestValidator.validate(requestDto);

        CommentResponseDto responseDto = commentService.createComment(scheduleId, requestDto);

        return new ResponseEntity<>(new CommonDto<>(HttpStatus.CREATED, "success", responseDto), HttpStatus.CREATED);
    }
}
