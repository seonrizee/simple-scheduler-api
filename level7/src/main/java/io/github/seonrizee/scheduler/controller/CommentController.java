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

/**
 * 특정 일정(Schedule)에 대한 댓글(Comment) 관련 HTTP 요청을 처리하는 컨트롤러
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/schedules/{scheduleId}/comments")
public class CommentController {

    private final CommentService commentService;
    private final CommentCreateRequestValidator commentCreateRequestValidator;

    /**
     * 특정 일정에 새로운 댓글을 생성합니다.
     *
     * @param scheduleId 댓글을 추가할 일정의 ID
     * @param requestDto 댓글 생성에 필요한 데이터 (내용, 작성자, 비밀번호)
     * @return 생성된 댓글 정보를 담은 ResponseEntity
     */
    @PostMapping
    public ResponseEntity<ApiResponse<CommentResponseDto>> createComment(
            @PathVariable Long scheduleId,
            @RequestBody CommentCreateRequestDto requestDto) {

        commentCreateRequestValidator.validate(requestDto);

        CommentResponseDto responseDto = commentService.createComment(scheduleId, requestDto);

        return ApiResponse.created(responseDto);
    }
}
