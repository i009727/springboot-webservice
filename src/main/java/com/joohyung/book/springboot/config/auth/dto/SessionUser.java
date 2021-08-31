package com.joohyung.book.springboot.config.auth.dto;

import com.joohyung.book.springboot.domain.user.User;
import lombok.Getter;

import java.io.Serializable;

@Getter
// 인증된 사용자 정보를 담는 클래스 => name, email, picture 외 정보(id, role)는 필요하지 않음
// 세션 저장을 위해서는 직렬화가 구현되어야 함.
// User클래스는 Entity이므로 직렬화 기능을 가진 dto를 개별로 구현하는것이 개발에 효율적이다.
public class SessionUser implements Serializable {
    private String name;
    private String email;
    private String picture;

    public SessionUser(User user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
    }
}
