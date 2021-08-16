package com.joohyung.book.springboot.service.posts;

import com.joohyung.book.springboot.domain.posts.Posts;
import com.joohyung.book.springboot.domain.posts.PostsRepository;
import com.joohyung.book.springboot.web.dto.PostsListResponseDto;
import com.joohyung.book.springboot.web.dto.PostsResponseDto;
import com.joohyung.book.springboot.web.dto.PostsSaveRequestDto;
import com.joohyung.book.springboot.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

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
        // DB에 쿼리를 전송하는 부분이 존재하지 않음 => JPA의 영속성 컨텍스트
        // 더티 체킹: 상태변경 검사 => 트랜잭션이 끝나는 시점에 상태변화가 존재하는
        //                          모든 엔티티 객체를 DB에 자동으로 반영.
        // 즉, Entity조회시, 스냅샷을 생성하고, tx가 끝날때 스냅샷과 비교하여 변경사항을 DB에 반영
        posts.update(requestDto.getTitle(), requestDto.getContent());
        return id;
    }

    @Transactional
    public void delete(Long id) {
        Posts posts = postsRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("해당 게시글이 없습니다. id=" + id));
        postsRepository.delete(posts);
    }

    // Transaction의 범위는 유지하지만, 조회기능만 가능하도록 설정
    // 등록, 수정, 삭제 기능이 없는 메소드에서 사용하면 조회속도가 향상됨.
    @Transactional(readOnly = true)
    public List<PostsListResponseDto> findAllDesc() {
        // 모든 posts entity를 조회하여 스트림으로 생성
        return postsRepository.findAllDesc().stream()
                // 생성된 스트림의 각 entity를 PostsListResponseDto객체로 매핑, List로 반환
                .map(PostsListResponseDto::new)
                .collect(Collectors.toList());
    }

    public PostsResponseDto findById(Long id) {
        Posts entity = postsRepository.findById(id).orElseThrow(() ->
                                    new IllegalArgumentException("해당 게시글이 없습니다. id: " + id));
        return new PostsResponseDto(entity);
    }
}
