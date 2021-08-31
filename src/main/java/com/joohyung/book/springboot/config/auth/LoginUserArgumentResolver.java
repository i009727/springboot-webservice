package com.joohyung.book.springboot.config.auth;

import com.joohyung.book.springboot.config.auth.dto.SessionUser;
import lombok.RequiredArgsConstructor;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Component
// HandlerMethodArgumentResolver:
// 컨트롤러의 메소드에 특정 조건에 맞는 파라미터가 있을때 원하는 값을 바인딩해주는 인터페이스
// ex) @RequestBody, @PathVariable
public class LoginUserArgumentResolver implements HandlerMethodArgumentResolver {

    private final HttpSession httpSession;

    // 컨트롤러 메소드의 특정 파라미터를 resolver가 지원하는지 판단하는 메소드
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        // 파라미터에 @LoginUser 어노테이션이 붙어있는지 여부를 확인
        boolean isLoginUserAnnotaion = parameter.getParameterAnnotation(LoginUser.class) != null;
        // 파라미터의 클래스 타입이 SessionUser와 같은지 확인
        boolean isUserClass = SessionUser.class.equals(parameter.getParameterType());
        return isLoginUserAnnotaion && isUserClass;
    }

    // 실제로 파라미터에 바인딩할 객체를 생성
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory)
                                        throws Exception {
        return httpSession.getAttribute("user");
    }
}

