package my.vono.web.config.oauth.dto;

import java.io.Serializable;

import lombok.Getter;
import my.vono.web.entity.Member;

@Getter
public class SessionUser implements Serializable{

	private String name;
	private String email;
	
	public SessionUser(Member member) {
		this.name = member.getName();
		this.email = member.getEmail();
	}
}
