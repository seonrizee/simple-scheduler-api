package io.github.seonrizee.scheduler.validator;

/**
 * DTO(Data Transfer Object)의 유효성을 검사하기 위한 공통 인터페이스. 이 인터페이스를 구현하는 클래스는 특정 DTO 타입에 대한 유효성 검사 로직을 제공해야 합니다.
 *
 * @param <T> 유효성을 검사할 DTO의 타입
 */
public interface Validator<T> {

    /**
     * 주어진 DTO 객체의 유효성을 검사합니다. 유효성 검사에 실패할 경우, RuntimeException을 발생시켜야 합니다.
     *
     * @param dto 유효성을 검사할 DTO 객체
     */
    void validate(T dto);
}
