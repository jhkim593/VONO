package my.vono.web.config.oauth;

import java.util.Collections;

import javax.servlet.http.HttpSession;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import my.vono.web.config.oauth.dto.OAuthAttributes;
import my.vono.web.config.oauth.dto.SessionUser;
import my.vono.web.entity.Member;
import my.vono.web.model.user.MemberDAO;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User>{
	
	private final MemberDAO memberDAO;
	private final HttpSession httpSession;
	
	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

		OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
		OAuth2User oAuth2User = delegate.loadUser(userRequest);
		
		String registrationId = userRequest.getClientRegistration().getRegistrationId();
		String userNameAttibuteName = userRequest.getClientRegistration().getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();
		
		OAuthAttributes attributes = OAuthAttributes.of(registrationId, userNameAttibuteName, oAuth2User.getAttributes());
		
		Member member = saveOrUpdate(attributes);
		httpSession.setAttribute("user", new SessionUser(member));
		
		return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority(member.getRole())),
				attributes.getAttributes(),
				attributes.getNameAttributeKey());
	}
	
	private Member saveOrUpdate(OAuthAttributes attributes) {
		Member member = memberDAO.findByLoginId(attributes.getEmail())
				.map(entity -> entity.update(attributes.getName()))
				.orElse(attributes.toEntity());
		
		return memberDAO.save(member);
	}
	
}