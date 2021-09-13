package com.joohyung.book.springboot.config.auth;

import com.joohyung.book.springboot.domain.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity      // SpringSecurity 설정 활성화
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // h2-console 화면 사용을 위한 설정
                .csrf().disable()
                .headers().frameOptions().disable()
                .and()
                    // URL별 권한 관리 설정을 위한 옵션의 시작점 => antMatchers 사용 가능
                    .authorizeRequests()
                    // 권한 관리 대상을 지정하는 옵션
                    // "/"등 지정된 URL들은 permitAll()을 통해 전체 열람 권한을 지정
                    .antMatchers("/", "/css/**", "/images/**", "/js/**", "/h2-console/**", "/profile")
                        .permitAll()
                    // "/api/v1/**"주소를 갖는 API는 USER권한을 갖는 사용자만 열람가능하게 지정
                    // Role.USER.name(): Enum USER 객체의 문자열 반환(USER)
                    .antMatchers("/api/v1/**").hasRole(Role.USER.name())
                    // anyRequest(): 설정된 값들 이외 나머지 URL을 의미
                    .anyRequest().authenticated()   // 나머지 URL은 인증된(로그인한) 사용자에게만 허용
                .and()
                    // 로그아웃 기능 설정
                    .logout()
                        .logoutSuccessUrl("/")  // 로그아웃 성공시 "/"로 이동
                .and()
                    // OAuth2 로그인 기능 설정에 대한 시작점
                    .oauth2Login()
                        // OAuth2로그인 성공 후 사용자 정보를 가져울때의 설정
                        .userInfoEndpoint()
                            // 소셜 로그인 성공시 진행할 UserService의 구현체 등록
                            .userService(customOAuth2UserService);
    }
}
