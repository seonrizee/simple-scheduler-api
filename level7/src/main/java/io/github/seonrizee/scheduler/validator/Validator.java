package io.github.seonrizee.scheduler.validator;

public interface Validator<T> {

    void validate(T dto);
}
