package com.joohyung.book.springboot.web;

import com.joohyung.book.springboot.config.auth.LoginUser;
import com.joohyung.book.springboot.config.auth.dto.SessionUser;
import com.joohyung.book.springboot.service.posts.PostsService;
import com.joohyung.book.springboot.web.dto.PostsListResponseDto;
import com.joohyung.book.springboot.web.dto.PostsResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
public class IndexController {      // 페이지에 관련된 Controller들
    private final PostsService postsService;
    private final HttpSession httpSession;
    // build.gradle의 mustache dependency로 인해
    // 앞의 경로와 뒤의 확장자가 자동으로 지정된다.
    // 앞의 경로: src/main/java/web/resources/templates
    // 뒤의 확장자: .mustache
    // 최종으로 반환되는 문자열: src/main/java/web/resources/templates/index.mustache
    @GetMapping("/")
    // @LoginUser ~: 메소드의 인자로 세션값을 받음
    public String index(Model model, @LoginUser SessionUser user) {
        // 게시글들을 model에 저장
        model.addAttribute("posts", postsService.findAllDesc());
        // 로그인한 사용자가 있다면 사용자이름을 model에 저장
        // @LoginUser 어노테이션을 통해 밑의 코드(로그인한 사용자를 가져오는 부분)를 대체
        // SessionUser user = (SessionUser) httpSession.getAttribute("user");
        if (user != null)
            model.addAttribute("userName", user.getName());
        return "index";
    }

    @GetMapping("/posts/save")
    public String postsSave() {
        return "posts-save";
    }

    @GetMapping("/posts/update/{id}")
    public String postsUpdate(@PathVariable Long id, Model model) {
        PostsResponseDto dto = postsService.findById(id);
        model.addAttribute("post", dto);
        return "posts-update";
    }

    @DeleteMapping("/api/v1/posts/{id}")
    public Long delete(@PathVariable Long id) {
        postsService.delete(id);
        return id;
    }
}
