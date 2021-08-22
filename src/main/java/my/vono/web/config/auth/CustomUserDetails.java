package my.vono.web.config.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import my.vono.web.entity.Member;

public class CustomUserDetails implements UserDetails, OAuth2User {
	
	private Member member;
	private GrantedAuthority authorities;
	private Map<String, Object> attributes;
	
	// 일반 로그인
	public CustomUserDetails(Member member, GrantedAuthority authorities) {
		this.authorities = authorities;
		this.member = member;
	}

	// OAuth 로그인
	public CustomUserDetails(Member member, GrantedAuthority authorities, Map<String, Object> attributes) {
		super();
		this.member = member;
		this.authorities = authorities;
		this.attributes = attributes;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		ArrayList<GrantedAuthority> auth = new ArrayList<>();
		auth.add(authorities);
		return auth;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return member.getPw();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return member.getName();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public Map<String, Object> getAttributes() {
		// TODO Auto-generated method stub
		return attributes;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}
	public Member getMember() {
		return member;
	}

}
