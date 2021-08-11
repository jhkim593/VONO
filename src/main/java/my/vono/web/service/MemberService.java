package my.vono.web.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import my.vono.web.entity.Folder;
import my.vono.web.entity.Member;
import my.vono.web.model.folder.FolderDAO;
import my.vono.web.model.member.MemberDAO;
import my.vono.web.model.member.MemberVO;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
	
	private final MemberDAO memberDAO;
	private final FolderDAO folderDAO;

	public void defaultSignUp(MemberVO memberVO) {
		
		Member member=Member.createMemeber(memberVO.getLogin_id(), memberVO.getPw(), null,
				memberVO.getName(),memberVO.getEmail(),
				 memberVO.getPhone(), memberVO.getJob());

        Folder folder=Folder.createFolder("기본폴더", member);
        
	    memberDAO.save(member);
	    folderDAO.save(folder);
	    
	}

//    public Boolean login(MemberVO memberVO) {
//    	Optional<Member> findMember=memberDAO.findByMemberWithLoginIdAndPw(memberVO.getLogin_id(),memberVO.getPw());
//    	if(findMember.isPresent()) {
//    		return true;
//    		
//    	}
//    	return false;
//    	
//    }
    
    public MemberVO detailMember(Long id) {
    	Optional<Member>findMember=memberDAO.findById(id);
    	if(findMember.isPresent()) {
    		Member member=findMember.get();
    		return MemberVO.createMemberVO(member);
    	}
    	return null;
    }
    
    public void updateMember(MemberVO memberVO) {
    	Optional<Member>findMember=memberDAO.findById(memberVO.getId());
    	if(findMember.isPresent()) {
    		
    		Member member=findMember.get();
    		
    		member.changeEmail(memberVO.getEmail());
    		
    	}
    	
    }
    public void deleteMember(Long id) {
    	Optional<Member>findMember=memberDAO.findById(id);
    	if(findMember.isPresent()) {
    		Member member=findMember.get();
    		memberDAO.delete(member);
    		
    	}
    		
    }
    
	


	

}
