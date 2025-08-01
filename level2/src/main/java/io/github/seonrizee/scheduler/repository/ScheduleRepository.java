package io.github.seonrizee.scheduler.repository;

import io.github.seonrizee.scheduler.entity.Schedule;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
    List<Schedule> findSchedulesByUsername(String username);
}
