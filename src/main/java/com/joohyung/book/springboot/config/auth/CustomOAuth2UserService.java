package com.joohyung.book.springboot.config.auth;

import com.joohyung.book.springboot.config.auth.dto.OAuthAttributes;
import com.joohyung.book.springboot.config.auth.dto.SessionUser;
import com.joohyung.book.springboot.domain.user.User;
import com.joohyung.book.springboot.domain.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Collections;

@RequiredArgsConstructor
@Service
// OAuth2 로그인 성공 이후 사용자의 정보(name, email, picture 등)를 기반으로
// 가입, 정보수정, 세션저장 등의 기능을 지원하는 쿨래스
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final UserRepository userRepository;
    private final HttpSession httpSession;

    // loadUser(): OAuth2공급자로부터 액세스 토큰을 얻은 후 실행되는 메소드
    // 즉, 로그인 이후 이메일, 이름 등의 정보를 이용하여 특정 작업을 처리하기위해 오버라이딩.
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        // DefaultOAuth2UserService: OAuth2UserService의 구현체, userRequest 내부 정보를 확인 가능
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        // registrationId: 로그인 진행중인 서비스를 구분하는 코드(구글인지, 네이버인지~)
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        // userNameAttributeName: OAuth2 로그인 진행시 키가되는 필드값 => primary key와 비슷
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName();
        // OAuthAttributes: OAuth2UserService를 통해 가져온 OAuth2User의 attribute를 담을 클래스
        // OAuthAttributes의 of()를 호출하여 Builder를 통해 OAuthAttributes 생성
        OAuthAttributes attributes = OAuthAttributes
                .of(registrationId, userNameAttributeName, oAuth2User.getAttributes());
        // OAuthAttributes내 정보를 통해 신규 저장, 업데이트 작업 처리
        User user = saveOrUpdate(attributes);
        // SesstionUser: 세션에 사용자 정보를 저장하기 위한 DTO클래스
        httpSession.setAttribute("user", new SessionUser(user));
        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),
                attributes.getAttributes(),
                attributes.getNameAttributeKey()
        );
    }

    private User saveOrUpdate(OAuthAttributes attributes) {
        // 입력한 정보와 일치하는 정보가 있는지 확인 => 이미 저장된 사용자인지 확인
        User user = userRepository.findByEmail(attributes.getEmail())
                // 이미 저장된 사용자라면 업데이트 => 구글 사용자 정보가 업데이트된 상황 대비
                .map(entity -> entity.update(attributes.getName(), attributes.getPicture()))
                // 이미 저장된 사용자가 아니라면 User정보 생성
                .orElse(attributes.toEntity());
        // 위에서 업데이트되거나 생성된 정보를 저장
        return userRepository.save(user);
    }
}
