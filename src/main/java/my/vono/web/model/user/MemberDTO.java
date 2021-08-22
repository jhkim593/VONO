package my.vono.web.model.user;

import javax.persistence.Column;

import my.vono.web.entity.Member;

public class MemberDTO {
	private Long id;

	private String login_id;
	
    private String pw;
    
  
    private String provider;
    

    private String name;

    private String email;
    
  
    private String phone;
    
    private String job;
    
    private String profile;
    
    private String role;
   
    public MemberDTO(Member member) {

    
	    	this.id=member.getId();
	    	this.name=member.getName();
	    	this.email=member.getEmail();
	    	this.job=member.getJob();
	    	this.phone=member.getPhone();
	    	this.provider= member.getProvider();
	    	this.profile=member.getProfile();
	    	
	    }
}
