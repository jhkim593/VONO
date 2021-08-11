package my.vono.web.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;

import org.springframework.boot.context.properties.bind.DefaultValue;

import com.sun.istack.NotNull;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    		String provider,String name,String email,String phone,String job) {
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
    public void changeEmail(String email) {
    	if(email!=null) {
    	this.email=email;
    	}
    
    }
    
    
}
