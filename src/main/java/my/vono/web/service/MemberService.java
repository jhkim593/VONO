package my.vono.web.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import my.vono.web.entity.Member;
import my.vono.web.model.user.MemberDAO;

@Service
@Transactional
public class MemberService {
	
	@Autowired
	MemberDAO memberDAO;
	
	
	public void inser(Long a) {
		memberDAO.findById(a);
	}
	
//	public void inser(Member m) {
//		memberDAO.delete(m);
//		memberDAO.save()
//	}
	

}
