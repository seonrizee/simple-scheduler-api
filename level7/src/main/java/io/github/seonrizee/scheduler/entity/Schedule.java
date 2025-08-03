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
 * 일정 정보를 나타내는 엔티티 클래스.
 */
@Entity
@Getter
@NoArgsConstructor
public class Schedule extends BaseDateEntity {

    /**
     * 일정의 고유 식별자 (Primary Key).
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 일정 제목.
     */
    @Column(nullable = false, length = 30)
    private String title;

    /**
     * 일정 내용.
     */
    @Column(nullable = false, length = 200)
    private String contents;

    /**
     * 일정을 생성한 사용자의 이름.
     */
    @Column(nullable = false, length = 30)
    private String username;

    /**
     * 일정 수정 및 삭제 시 필요한 비밀번호.
     */
    @Column(nullable = false)
    private String password;

    /**
     * Schedule 엔티티를 생성합니다.
     *
     * @param title    일정 제목
     * @param contents 일정 내용
     * @param username 작성자명
     * @param password 비밀번호
     */
    @Builder
    public Schedule(String title, String contents, String username, String password) {
        this.title = title;
        this.contents = contents;
        this.username = username;
        this.password = password;
    }

    /**
     * 일정의 제목과 작성자명을 수정합니다.
     *
     * @param title    수정할 제목
     * @param username 수정할 작성자명
     */
    public void updateById(String title, String username) {
        this.title = title;
        this.username = username;
    }
}
