package com.joohyung.book.springboot.domain.posts;


import org.apache.tomcat.jni.Local;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

// SpringBootTest: 스프링이 관리하는 모든 bean을 등록시킨 후 테스트 => 무거움
// WebMvcTest: Web layer에 관련된 bean만 등록시킨 후 테스트 => 가벼움
//              ==> Service bean은 등록되지 않으므로 가짜 bean을 만들어 주어야함(EX: MockMvc)
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class PostsRespositoryTest {
    @Autowired
    PostsRepository postsRepository;

    @AfterEach                              // Junit에서 단위 테스트가 끝날때마다 수행될 메소드
    public void cleanup() {                 // 테스트 데이터간 침범을 막기 위함 => 테스트용 데이터를 삭제
        postsRepository.deleteAll();
    }

    @Test
    public void 게시글저장_불러오기() {
        String title = "테스트 게시글";
        String content = "테스트 본문";
        postsRepository.save(Posts.builder()        // save(): posts 테이블에 insert/update 쿼리를 실행
                .title(title)                       // id값이 이미 있다면 update, id값이 없다면 insert
                .content(content)
                .author("joohyung@gmail.com")
                .build());
        List<Posts> postsList = postsRepository.findAll();  // posts 테이블의 모든 데이터를 조회

        Posts posts = postsList.get(0);
        assertThat(posts.getTitle()).isEqualTo(title);
        assertThat(posts.getContent()).isEqualTo(content);
    }

    @Test
    public void BaseTimeEntity_등록() {
        LocalDateTime now = LocalDateTime
                .of(2019, 6, 4, 0, 0, 0);
        postsRepository.save(Posts.builder()
                .title("title")
                .content("content")
                .author("author")
                .build());
        List<Posts> postsList = postsRepository.findAll();
        Posts posts = postsList.get(0);
        System.out.println(">>>>>>> createDate: " + posts.getCreatedDate()
                + ", modifiedDate: " + posts.getModifiedDate());
        assertThat(posts.getCreatedDate()).isAfter(now);
        assertThat(posts.getModifiedDate()).isAfter(now);
    }
}
