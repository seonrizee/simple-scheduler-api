package io.github.seonrizee.scheduler.exception;

/**
 * 제공된 비밀번호가 일치하지 않을 때 발생하는 예외.
 */
public class InvalidPasswordException extends RuntimeException {

    private static final String MESSAGE = "비밀번호가 일치하지 않습니다.";

    public InvalidPasswordException() {
        super(MESSAGE);
    }
}