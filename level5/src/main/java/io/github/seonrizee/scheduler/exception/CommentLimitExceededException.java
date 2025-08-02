package io.github.seonrizee.scheduler.exception;

public class CommentLimitExceededException extends RuntimeException {

    private static final String MESSAGE = "댓글은 10개까지 등록할 수 있습니다.";

    public CommentLimitExceededException() {
        super(MESSAGE);
    }

}
