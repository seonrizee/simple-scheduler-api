package io.github.seonrizee.scheduler.repository;

import io.github.seonrizee.scheduler.entity.Schedule;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Schedule 엔티티에 대한 데이터베이스 액세스를 처리하는 리포지토리 인터페이스.
 */
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    /**
     * 특정 사용자가 작성한 모든 일정을 수정된 날짜의 내림차순으로 조회합니다.
     *
     * @param username 조회할 사용자의 이름
     * @return 해당 사용자의 일정 목록
     */
    List<Schedule> findByUsernameOrderByUpdatedAtDesc(String username);

    /**
     * 모든 일정을 수정된 날짜의 내림차순으로 조회합니다.
     *
     * @return 모든 일정 목록
     */
    List<Schedule> findAllByOrderByUpdatedAtDesc();
}
