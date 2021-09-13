package com.joohyung.book.springboot.web;

import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class ProfileController {

    // Profile: Spring의 각 컴포넌트 설정에 필요한 환경변수 값들을 모은 파일
    // 기본 profile: application.properties
    // 참고: https://javachoi.tistory.com/224

    private final Environment env;

    @GetMapping("/profile")
    public String profile() {
        // env.getActiveProfiles(): 현재 실행중인 active_profile을 모두 리턴
        // 현재 존재하는 profile: real, oauth, real-db
        List<String> profiles = Arrays.asList(env.getActiveProfiles());
        List<String> realProfiles = Arrays.asList("real", "real1", "real2");
        String defaultProfile = profiles.isEmpty()? "default" : profiles.get(0);

        return profiles.stream()
                .filter(realProfiles :: contains)
                .findAny()
                .orElse(defaultProfile);
    }
}
