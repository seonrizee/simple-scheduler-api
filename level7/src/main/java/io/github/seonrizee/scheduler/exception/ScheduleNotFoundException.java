package io.github.seonrizee.scheduler.exception;

public class ScheduleNotFoundException extends RuntimeException {

    private static final String MESSAGE = "선택한 일정이 존재하지 않습니다.";

    public ScheduleNotFoundException() {
        super(MESSAGE);
    }
}
