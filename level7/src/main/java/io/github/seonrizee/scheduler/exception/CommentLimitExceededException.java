package io.github.seonrizee.scheduler.exception;

/**
 * 하나의 일정에 작성할 수 있는 최대 댓글 개수를 초과했을 때 발생하는 예외.
 */
public class CommentLimitExceededException extends RuntimeException {

    private static final String MESSAGE = "하나의 일정에는 최대 10개의 댓글만 작성할 수 있습니다.";

    public CommentLimitExceededException() {
        super(MESSAGE);
    }
}