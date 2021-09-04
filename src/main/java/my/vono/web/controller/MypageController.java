package my.vono.web.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import lombok.RequiredArgsConstructor;
import my.vono.web.config.auth.CustomUserDetails;
import my.vono.web.entity.Member;
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
	

	@RequestMapping(value="mypage",method = {RequestMethod.POST})
	public String update_info(Model model,@ModelAttribute("mypageUpdate") MemberVO membervo,@AuthenticationPrincipal CustomUserDetails custom ) {
		//비밀번호 체크
		membervo.setId(custom.getMember().getId());
		Member member = custom.getMember();
		model.addAttribute("info_loginid", member.getLogin_id());
		model.addAttribute("info_name", membervo.getName());
		model.addAttribute("info_email", membervo.getEmail());
		model.addAttribute("info_phone", membervo.getPhone());
		model.addAttribute("info_job", membervo.getJob());
		model.addAttribute("info_provider", membervo.getProvider());
		System.out.println(membervo.getLogin_id());
		memberService.updateMember(membervo);
		
		return "mypageHtml/mypage";
		
	}

	/*
	 * @RequestMapping(value="mypage/delete",method = {RequestMethod.DELETE}) public
	 * String delete_member(@AuthenticationPrincipal CustomUserDetails custom) {
	 * Long id = custom.getMember().getId(); memberService.deleteMember(id); return
	 * "redirect:/"; }
	 */
	
}
	
	




