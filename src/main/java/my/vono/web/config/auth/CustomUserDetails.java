package my.vono.web.config.auth;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import my.vono.web.entity.Member;

public class CustomUserDetails implements UserDetails {
	
	private Member member;
	private GrantedAuthority authorities;
	
	public CustomUserDetails(Member member, GrantedAuthority authorities) {
		this.member = member;
		this.authorities = authorities;
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

}
