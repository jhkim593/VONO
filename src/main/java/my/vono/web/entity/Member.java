package my.vono.web.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
//@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
	@Column(nullable = false)
	private String login_id;
	
	@Column(nullable = false)
    private String pw;
    
    @Column(nullable = true)
    private String provider;
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false)
    private String email;
    
    @Column(nullable = false)
    private String phone;
    
    private String job;
    
    private String profile;
    
    private String role;
    
    @OneToMany(mappedBy = "member")    //member와 folder 일대 다 관계 설정
    private List<Folder>folders=new ArrayList<>();
    
    public static Member createMemeber(String login_id,String pw,
    		String provider, String name,String email,String phone,String job) {
    	Member m=new Member();
    	m.login_id=login_id;
    	m.pw=pw;
    	m.provider=provider;
    	m.name=name;
    	m.email=email;
    	m.phone=phone;
    	m.job=job;
    	m.role="user";
		return m;    
	}
    public void updateMember(String name,String email,String phone,String job) {
    	if(name!=null) {
    	this.name=name;
    	}	
    	if(email!=null) {
        	this.email=email;
        	}
    	if(phone!=null) {
        	this.phone=phone;
        	}
    	if(job!=null) {
        	this.job=job;
        	}
    
    }
    
    @Builder
    public Member(String name, String email, String role) {
    	this.name = name;
    	this.email = email;
    	this.role = role;
    }
    
    public Member update(String name) {
    	this.name = name;
    	
    	return this;
    }
    
   
}
