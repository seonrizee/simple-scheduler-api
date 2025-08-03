package io.github.seonrizee.scheduler.repository;

import io.github.seonrizee.scheduler.entity.Comment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Comment 엔티티에 대한 데이터베이스 액세스를 처리하는 리포지토리 인터페이스.
 */
public interface CommentRepository extends JpaRepository<Comment, Long> {
    /**
     * 특정 일정에 달린 댓글의 개수를 조회합니다.
     *
     * @param scheduleId 댓글 개수를 조회할 일정의 ID
     * @return 해당 일정의 댓글 개수
     */
    long countByScheduleId(Long scheduleId);

    /**
     * 특정 일정에 달린 모든 댓글을 조회합니다.
     *
     * @param scheduleId 댓글을 조회할 일정의 ID
     * @return 해당 일정의 댓글 목록
     */
    List<Comment> findAllByScheduleId(Long scheduleId);
}
