package io.github.seonrizee.scheduler.exception;

/**
 * 요청한 ID에 해당하는 일정을 찾을 수 없을 때 발생하는 예외.
 */
public class ScheduleNotFoundException extends RuntimeException {

    private static final String MESSAGE = "선택한 일정이 존재하지 않습니다.";

    public ScheduleNotFoundException() {
        super(MESSAGE);
    }
}
