package io.github.seonrizee.scheduler.entity;

import io.github.seonrizee.scheduler.dto.request.CommentCreateRequestDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Comment extends BaseDateEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "schedule_id")
    private Long scheduleId;

    @Column(nullable = false, length = 100)
    private String contents;

    @Column(nullable = false, length = 30)
    private String username;

    @Column(nullable = false)
    private String password;

    public Comment(Long scheduleId, CommentCreateRequestDto requestDto) {
        this.scheduleId = scheduleId;
        this.contents = requestDto.getContents();
        this.username = requestDto.getUsername();
        this.password = requestDto.getPassword();
    }
}
