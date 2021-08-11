package my.vono.web.model.member;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import my.vono.web.entity.Member;

@Getter
@Setter
@AllArgsConstructor
public class MemberVO {

	private Long id;
	
	private String login_id;
	
	private String pw;
	
	private String name;
	
	private String provider;
	
	private String email;
	
	private String phone;
	
	private String job;
	
	private String profile;
	
	public static MemberVO createMemberVO(Member member) {
		return new MemberVO(member.getId(),member.getLogin_id(),member.getPw(),member.getName(),member.getProvider(),
				member.getEmail(),member.getPhone(),member.getJob(),member.getProfile());
	}
}
