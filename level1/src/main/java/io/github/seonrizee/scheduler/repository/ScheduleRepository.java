package io.github.seonrizee.scheduler.repository;

import io.github.seonrizee.scheduler.entity.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule, Long> {
}
