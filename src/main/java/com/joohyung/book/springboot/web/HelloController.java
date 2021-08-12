package com.joohyung.book.springboot.web;

import com.joohyung.book.springboot.web.dto.HelloResponseDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// 일반 컨트롤러가 아닌 JSON을 반환하는 컨트롤러
// 각 메소드마다 선언되는 @ResponseBody 어노테이션 역할
@RestController
public class HelloController {
    // HTTP GET Method 요청을 받기위한 API생성
    @GetMapping("/hello")
    public String hello() {
        return "hello";
    }
    @GetMapping("/hello/dto")
    // @RequestParam: 외부에서 API로 넘긴 파라미터를 가져오는 어노테이션
    public HelloResponseDto helloDto(@RequestParam("name") String name,
                                     @RequestParam("amount") int amount) {
        return new HelloResponseDto(name, amount);
    }
}
