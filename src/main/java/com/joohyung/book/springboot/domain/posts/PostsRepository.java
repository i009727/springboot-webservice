package com.joohyung.book.springboot.domain.posts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

// Posts 클래스로 DB에 접근하게 해줄 인터페이스, DB Layer 접근자로 DAO와 유사
// JpaRepository 상속시 기본적인 CRUD 메소드가 자동으로 생성됨.
public interface PostsRepository extends JpaRepository<Posts, Long> {
    // Spring data JPA에서 제공하지 않는 기능을 위한 쿼리 메소드.
    // 등록/수정/삭제 작업은 SpringDataJpa로 가능하지만
    // 복잡한 조회기능은 Entity만으로 처리가 어려움
    @Query("SELECT p FROM Posts p ORDER BY p.id DESC")
    List<Posts> findAllDesc();
}
