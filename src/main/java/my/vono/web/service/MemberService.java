package my.vono.web.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import my.vono.web.entity.Member;
import my.vono.web.model.user.MemberDAO;
import my.vono.web.model.user.MemberVO;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
	
	private final MemberDAO memberDAO;
	
	
	public Member defaultSignUp(Member member) {
		

		return memberDAO.save(member);
	}
	
	public Member login(String login_Id, String pw) {
		
		return null;
		
	}
//	public void inser(Member m) {
//		memberDAO.delete(m);
//		memberDAO.save()
//	}
	

}
