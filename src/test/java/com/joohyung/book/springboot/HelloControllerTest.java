package com.joohyung.book.springboot;

import com.joohyung.book.springboot.web.HelloController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// 테스트 진행시 Junit의 실행자와 함께 Springrunner라는 실행자가 실행되도록 설정
// 스프링 부트 테스트와 JUnit사이의 연결자
@ExtendWith(SpringExtension.class)
// Web에만 집중하도록 하는 스프링 테스트 어노테이션
@WebMvcTest(controllers = HelloController.class)
public class HelloControllerTest {
    // 스프링이 관리하는 bean을 주입받음
    @Autowired
    // 웹 API테스트를 위해 사용
    private MockMvc mvc;

    @Test
    public void hello_returned() throws Exception {
        String hello = "hello";
        // MockMvc를 통해 /hello주소로 HTTP GET요청
        mvc.perform(get("/hello"))
                // perform()의 결과 입증 1: HTTP header의 status(200, 404, 500 등)
                .andExpect(status().isOk())
                // perform()의 결과 입증 2: 본문의 내용을 검증
                .andExpect(content().string(hello));
    }

    @Test
    public void helloDto_returned() throws Exception {
        String name = "hello";
        int amount = 1000;
        // param: API테스트시 사용될 요청 파라미터 설정(String값만 가능)
        // jsonPath: JSON 응답 값을 필드별로 검증가능한 메소드($.[필드명])
        mvc.perform(get("/hello/dto")
                .param("name", name)
                .param("amount", String.valueOf(amount)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(name)))
                .andExpect(jsonPath("$.amount", is(amount)));
    }
}
