package my.vono.web.service;

import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import my.vono.web.entity.Folder;
import my.vono.web.entity.Member;
import my.vono.web.exception.MemberNotFoundException;
import my.vono.web.model.folder.FolderRepository;
import my.vono.web.model.user.MemberRepository;
import my.vono.web.model.user.MemberVO;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {
	
	private final MemberRepository memberRepository;
	private final FolderRepository folderRepository;
	private final BCryptPasswordEncoder passwordEncoder;

	public void defaultSignUp(MemberVO memberVO) {
		
        String rawPassword = memberVO.getPw();
		String encPassword = passwordEncoder.encode(rawPassword);
		
		memberVO.setPw(encPassword);
		
		Member member=Member.createMemeber(memberVO.getLogin_id(), memberVO.getPw(), null,
				memberVO.getName(),memberVO.getEmail(), memberVO.getPhone(), memberVO.getJob());

        Folder folder=Folder.createFolder("Basic", member);

		
	    memberRepository.save(member);
	    folderRepository.save(folder);
	    
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
    	Optional<Member>findMember= memberRepository.findById(id);
    	if(findMember.isPresent()) {
    		Member member=findMember.get();
    		return MemberVO.createMemberVO(member);
    	}
    	return null;
    }
    
    public void updateMember(MemberVO memberVO) {
    	Member findMember= memberRepository.findById(memberVO.getId()).orElseThrow(MemberNotFoundException::new);
    		
    		findMember.updateMember(memberVO.getName(),memberVO.getEmail(),memberVO.getPhone(),memberVO.getJob());
    		
    	}
    	
    
    public void deleteMember(Long id) {
    	Optional<Member>findMember= memberRepository.findById(id);
    	if(findMember.isPresent()) {
    		Member member=findMember.get();
    		memberRepository.delete(member);
    		
    	}
    	
   

		
    }
    
}
