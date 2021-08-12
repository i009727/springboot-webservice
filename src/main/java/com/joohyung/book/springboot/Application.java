package com.joohyung.book.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// Application: 전체 프로젝트의 메인 클래스
// springboot의 가장 기본적인 설정을 선언해주는 어노테이션
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        // 내장 WAS(Web Application Server)실행
        SpringApplication.run(Application.class, args);
    }
}
