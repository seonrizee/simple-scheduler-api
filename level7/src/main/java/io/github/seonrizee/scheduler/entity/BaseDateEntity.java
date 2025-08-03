package io.github.seonrizee.scheduler.entity;


import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 * 생성 및 수정 일시를 자동으로 관리하기 위한 추상 클래스. JPA Auditing 기능을 사용하여 엔티티의 생성 및 수정 시간을 자동으로 기록합니다.
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Getter
public abstract class BaseDateEntity {

    /**
     * 엔티티 생성 일시. 처음 저장될 때 현재 시간이 자동으로 할당되며, 이후에는 변경되지 않습니다.
     */
    @CreatedDate
    @Column(updatable = false, name = "created_at")
    private LocalDateTime createdAt;

    /**
     * 엔티티 마지막 수정 일시. 엔티티가 수정될 때마다 현재 시간이 자동으로 갱신됩니다. 초기값은 createdAt과 동일합니다.
     */
    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}