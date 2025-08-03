package io.github.seonrizee.scheduler.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 특정 일정에 대한 댓글 정보를 나타내는 엔티티 클래스.
 */
@Entity
@Getter
@NoArgsConstructor
public class Comment extends BaseDateEntity {

    /**
     * 댓글의 고유 식별자 (Primary Key).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 이 댓글이 속한 일정(Schedule)의 ID.
     */
    @Column(nullable = false, name = "schedule_id")
    private Long scheduleId;

    /**
     * 댓글 내용.
     */
    @Column(nullable = false, length = 100)
    private String contents;

    /**
     * 댓글을 생성한 사용자의 이름.
     */
    @Column(nullable = false, length = 30)
    private String username;

    /**
     * 댓글 수정 및 삭제 시 필요한 비밀번호.
     */
    @Column(nullable = false)
    private String password;

    /**
     * Comment 엔티티를 생성합니다.
     *
     * @param scheduleId 댓글이 속한 일정의 ID
     * @param contents   댓글 내용
     * @param username   작성자명
     * @param password   비밀번호
     */
    @Builder
    public Comment(Long scheduleId, String contents, String username, String password) {
        this.scheduleId = scheduleId;
        this.contents = contents;
        this.username = username;
        this.password = password;
    }
}
