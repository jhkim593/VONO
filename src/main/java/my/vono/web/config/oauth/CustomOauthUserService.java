package my.vono.web.config.oauth;

import java.util.Map;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import my.vono.web.config.auth.CustomUserDetails;
import my.vono.web.entity.Member;
import my.vono.web.model.user.MemberDAO;

@Service
@RequiredArgsConstructor
public class CustomOauthUserService extends DefaultOAuth2UserService{
	
	private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	private final MemberDAO memberDAO;
	
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		
		// userRequest 정보
		// 구글로그인 버튼 클릭 -> 구글로그인창 -> 로그인 완료 -> code return(OAuth-Client 라이브러리) -> AccessToken 요청
		// loadUser함수를 통해 회원 프로필을 받아옴
		OAuth2User oauth2User = super.loadUser(userRequest);
		
		System.out.println("getAttributes: " + oauth2User.getAttributes());
		
		// google
		String provider = "google";
		String providerId = oauth2User.getAttribute("sub");
		String name = (String)oauth2User.getAttributes().get("name");
		String pw = passwordEncoder.encode("hivono");
		String email = (String)oauth2User.getAttributes().get("email");
		String role = "user";
		
		System.out.println(provider);
		System.out.println(name);
		System.out.println(email);
		System.out.println(role);

		// 이미 회원가입이 되어있는 경우
		Member member = memberDAO.findByEmail(email);
		
		if(member == null) {
			member = Member.builder()
					.login_id("google_"+providerId)
					.name(name)
					.pw(pw)
					.email(email)
					.role(role)
					.provider(provider)
					.build();
			memberDAO.save(member);
		}
		
		return new CustomUserDetails(member, new SimpleGrantedAuthority(member.getRole()), oauth2User.getAttributes());
	}

}
