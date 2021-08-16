package com.joohyung.book.springboot.domain;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass       // JAP Entity 클래스들이 해당 클래스를 상속할 경우 필드 또한 칼럼으로 인식하도록 설정
@EntityListeners(AuditingEntityListener.class)      // 해당 클래스에 Auditing기능을 포함
public class BaseTimeEntity {
    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime modifiedDate;
}
