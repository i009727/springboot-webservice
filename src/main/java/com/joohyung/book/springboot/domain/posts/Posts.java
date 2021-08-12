package com.joohyung.book.springboot.domain.posts;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter                 // 클래스 내 모든 필드의 Getter 메소드 자동 생성(Lombok annotation)
@NoArgsConstructor      // 클래스의 기본 생성자 자동 추가(Lombok annotation)
@Entity                 // 테이블과 링크될 클래스임을 나타냄(JPA annotation)
public class Posts {
    @Id                 // 해당 테이블의 기본키(PK) 필드
    @GeneratedValue(strategy = GenerationType.IDENTITY)         // PK(기본키)의 생성규칙, 일반적으로 Long타입 이용
    private Long id;                                            // GenerationType.IDENTITY: auto_increment속성

    @Column(length = 500, nullable = false)                     // 테이블의 칼럼(Column)을 의미(미선언시에도 칼럼으로 존재)
    private String title;                                       // 하지만, 기본값 외 추가 변경사항(Length속성 변경)이 있을때 선언

    @Column(columnDefinition = "TEXT", nullable = false)        // 테이블의 칼럼으로 Type속성을 TEXT로 변경(기본: VARCHAR)
    private String content;

    private String author;

    @Builder                                                    // 해당 클래스의 빌더 패턴 클래스를 생성
    public Posts(String title, String content, String author) { // 객체 생성 시 값을 입력할 필드를 명확히 할 수 있다.
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
