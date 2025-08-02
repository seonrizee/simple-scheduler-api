package io.github.seonrizee.scheduler.repository;

import io.github.seonrizee.scheduler.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    long countByScheduleId(Long scheduleId);
}

