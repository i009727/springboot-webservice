package com.joohyung.book.springboot.service.posts;

import com.joohyung.book.springboot.domain.posts.Posts;
import com.joohyung.book.springboot.domain.posts.PostsRepository;
import com.joohyung.book.springboot.web.dto.PostsResponseDto;
import com.joohyung.book.springboot.web.dto.PostsSaveRequestDto;
import com.joohyung.book.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class PostsService {
    // Autowired대신 생성자를 통해 bean을 주입받는 방식 이용
    // @RequiredArgsConstructor 어노테이션: 자동으로 final필드에 대해 생성자를 생성해준다.
    private final PostsRepository postsRepository;

    @Transactional
    public Long save(PostsSaveRequestDto requestDto) {
        return postsRepository.save(requestDto.toEntity()).getId();
    }

    @Transactional
    public Long update(Long id, PostsUpdateRequestDto requestDto) {
        Posts posts = postsRepository.findById(id).orElseThrow(() ->
                                    new IllegalArgumentException("해당 게시글이 없습니다. id: " + id));
        posts.update(requestDto.getTitle(), requestDto.getContent());
        return id;
    }

    public PostsResponseDto findById(Long id) {
        Posts entity = postsRepository.findById(id).orElseThrow(() ->
                                    new IllegalArgumentException("해당 게시글이 없습니다. id: " + id));
        return new PostsResponseDto(entity);
    }
}
