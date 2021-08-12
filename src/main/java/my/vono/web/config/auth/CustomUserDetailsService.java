package my.vono.web.config.auth;

import java.util.Optional;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import my.vono.web.entity.Member;
import my.vono.web.model.user.MemberDAO;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
	
	private final MemberDAO memberDAO;

	@Override
	public UserDetails loadUserByUsername(String name) throws UsernameNotFoundException {
		
		System.out.println("name : " + name);
		Member member = memberDAO.findByLoginId(name)
				.orElseThrow(() -> new UsernameNotFoundException(name));
		
		System.out.println("member : " + member);
		
		if(member != null) {
			return new CustomUserDetails(member, new SimpleGrantedAuthority(member.getRole()));
			
		}
		return null;
	}

}

