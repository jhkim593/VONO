package my.vono.web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;
import my.vono.web.config.auth.CustomUserDetails;
import my.vono.web.config.auth.CustomUserDetailsService;
import my.vono.web.entity.Member;
import my.vono.web.exception.BasicFolderRenameException;
import my.vono.web.exception.FolderAlreadyExistException;
import my.vono.web.exception.FolderNotFoundException;
import my.vono.web.exception.MemberNotFoundException;
import my.vono.web.model.response.DefaultResponseDto;
import my.vono.web.model.user.MemberDAO;
import my.vono.web.model.user.MemberDTO;
import my.vono.web.model.user.MemberVO;
import my.vono.web.service.MemberService;

@Controller
@RequiredArgsConstructor
public class MypageController {
	
	private final MemberService memberService;
	
	
	
	/**
	 *	회원가입 
	 */
	@GetMapping("mypage")
	public String mypage_info(Model model,@AuthenticationPrincipal CustomUserDetails custom ) {

		Member member = custom.getMember();
		
		model.addAttribute("info_loginid", member.getLogin_id());
		model.addAttribute("info_name", member.getName());
		model.addAttribute("info_email", member.getEmail());
		model.addAttribute("info_phone", member.getPhone());
		model.addAttribute("info_job", member.getJob());
		model.addAttribute("info_provider", member.getProvider());
		
		return "mypageHtml/mypage";
	

	
	}
	

	@PostMapping("mypage/update")
	public String update_info(Model model,@ModelAttribute MemberVO membervo,@AuthenticationPrincipal CustomUserDetails custom ) {
		
		Member member = custom.getMember();
		
		model.addAttribute("info_loginid", member.getLogin_id());
		model.addAttribute("info_name", member.getName());
		model.addAttribute("info_email", member.getEmail());
		model.addAttribute("info_phone", member.getPhone());
		model.addAttribute("info_job", member.getJob());
		model.addAttribute("info_provider", member.getProvider());
		
		memberService.updateMember(MemberVO.createMemberVO(custom.getMember()));
		
		return "mypageHtml/update";
		
	}

	@RequestMapping(value="mypage/delete", method=RequestMethod.DELETE)
	public void delete_member(@AuthenticationPrincipal CustomUserDetails custom) {
		Long id = custom.getMember().getId();
		memberService.deleteMember(id);
	}
	
}
	
	




