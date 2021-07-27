package my.vono.web.model.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
}
