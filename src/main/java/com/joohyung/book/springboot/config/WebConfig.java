package com.joohyung.book.springboot.config;

import com.joohyung.book.springboot.config.auth.LoginUserArgumentResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@RequiredArgsConstructor
@Configuration
// LoginUserArgumentResolver가 Spring에서 인식될 수 있도록 WebMvcConfigure에 추가
// HandlerMethodArgumentResolver는 항상 WebMvcConfigure의 addArgumentResolvers()로 추가해야 한다
public class WebConfig implements WebMvcConfigurer {

    private final LoginUserArgumentResolver loginUserArgumentResolver;

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(loginUserArgumentResolver);
    }
}
