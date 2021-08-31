package com.joohyung.book.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

// Application: 전체 프로젝트의 메인 클래스
//@EnableJpaAuditing              // JPA Auditing 활성화
@SpringBootApplication          // springboot의 가장 기본적인 설정을 선언해주는 어노테이션
public class Application {
    public static void main(String[] args) {
        // 내장 WAS(Web Application Server)실행
        SpringApplication.run(Application.class, args);
    }
}
