package com.joohyung.book.springboot.web;

import com.joohyung.book.springboot.service.posts.PostsService;
import com.joohyung.book.springboot.web.dto.PostsResponseDto;
import com.joohyung.book.springboot.web.dto.PostsSaveRequestDto;
import com.joohyung.book.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class PostsApiController {
    // Autowired대신 생성자를 통해 bean을 주입받는 방식 이용
    // 즉, lombok @RequiredArgsConstructor 어노테이션이 만든 생성자를 통해 bean을 주입받음.
    private final PostsService postsService;

    @PostMapping("/api/v1/posts")
    public Long save(@RequestBody PostsSaveRequestDto requestDto) {
        return postsService.save(requestDto);
    }

    @PutMapping("/api/v1/posts/{id}")
    public Long update(@PathVariable Long id, @RequestBody PostsUpdateRequestDto requestDto) {
        return postsService.update(id, requestDto);
    }

    @GetMapping("/api/v1/posts/{id}")
    public PostsResponseDto findById(@PathVariable Long id) {
        return postsService.findById(id);
    }

}
