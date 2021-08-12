package com.joohyung.book.springboot.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;

// Posts 클래스로 DB에 접근하게 해줄 인터페이스, DB Layer 접근자로 DAO와 유사
// JpaRepository 상속시 기본적인 CRUD 메소드가 자동으로 생성됨.
public interface PostsRepository extends JpaRepository<Posts, Long> {}
