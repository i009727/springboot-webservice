package com.joohyung.book.springboot.domain.user;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

// LOMBOK
// @NoArgsConstructor: 파라미터가 없는 기본 생성자 생성
// @AllArgsConstructor: 모든 필드값을 파라미터로 갖는 생성자 생성
// @RequiredArgsConstructor: final, @NonNull인 필드값만 파라미터로 갖는 생성자 생성

@Getter
@RequiredArgsConstructor
public enum Role {
    // SpringSecurity에서는 권한코드에 항상 "ROLE_"이 붙어야한다.
    GUEST("ROLE_GUEST", "손님"),
    USER("ROLE_USER", "일반 사용자");

    private final String key;
    private final String title;
}
