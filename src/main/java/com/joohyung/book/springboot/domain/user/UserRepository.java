package com.joohyung.book.springboot.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// User의 CRUD를 담당
public interface UserRepository extends JpaRepository<User, Long> {

    // 소셜 로그인을 통해 반환되는 값 중 email을 통해 이미 생성된 사용자인지 판단하기 위한 메소드
    Optional<User> findByEmail(String email);
}
