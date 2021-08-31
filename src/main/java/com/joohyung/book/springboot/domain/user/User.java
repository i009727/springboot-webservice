package com.joohyung.book.springboot.domain.user;

import com.joohyung.book.springboot.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

// 사용자 정보를 담당할 도메인 User

@Getter
@NoArgsConstructor
@Entity
public class User extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String email;

    @Column
    private String picture;

    // Enum의 기본타입인 int대신 의믜있는 데이터를 위해 String타입으로 변경
    @Enumerated(EnumType.STRING)    // JPA로 DB저장시 enum값의 타입을 결정
    @Column(nullable = false)
    private Role role;

    @Builder
    public User(String name, String email, String picture, Role role) {
        this.name = name;
        this.email = email;
        this.picture = picture;
        this.role = role;
    }

    public User update(String name, String picture) {
        this.name = name;
        this.picture = picture;
        return this;
    }

    public String getRoleKey() {
        // lombok으로 생성된 Role의 getKey() 메소드 호출
        return this.role.getKey();
    }
}
