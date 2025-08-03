package io.github.seonrizee.scheduler.controller;

import io.github.seonrizee.scheduler.dto.request.CommentCreateRequestDto;
import io.github.seonrizee.scheduler.dto.response.ApiResponse;
import io.github.seonrizee.scheduler.dto.response.CommentResponseDto;
import io.github.seonrizee.scheduler.service.CommentService;
import io.github.seonrizee.scheduler.validator.CommentCreateRequestValidator;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<ApiResponse<CommentResponseDto>> createComment(
            @PathVariable Long scheduleId,
            @RequestBody CommentCreateRequestDto requestDto) {

        commentCreateRequestValidator.validate(requestDto);

        CommentResponseDto responseDto = commentService.createComment(scheduleId, requestDto);

        return ApiResponse.created(responseDto);
    }
}
