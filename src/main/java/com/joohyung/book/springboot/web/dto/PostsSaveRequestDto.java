package com.joohyung.book.springboot.web.dto;

import com.joohyung.book.springboot.domain.posts.Posts;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

// Controller와 Service에서 사용할 Dto 클래스
// Entity(Posts.java)와 유사하지만, Entity는 Request/Response로 사용 불가능.
// Entity클래스는 DB와 링크된 핵심 클래스로 Entity의 변경은 다른 클래스에 큰 영향을 미침.
// DTO클래스는 Entity클래스와 달리 변경이 자주 일어나므로 이들간의 분리가 필요.
// 즉, Entity: DB Layer / DTO: View Layer
@Getter
@NoArgsConstructor
public class PostsSaveRequestDto {
    private String title;
    private String content;
    private String author;

    @Builder
    public PostsSaveRequestDto(String title, String content, String author) {
        this.title = title;
        this.content = content;
        this.author = author;
    }

    public Posts toEntity() {
        return Posts.builder()
                .title(title)
                .content(content)
                .author(author)
                .build();
    }
}
