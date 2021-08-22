package my.vono.web.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;
import my.vono.web.config.auth.CustomUserDetails;
import my.vono.web.model.user.MemberVO;
import my.vono.web.service.MemberService;

@Controller
@RequiredArgsConstructor
public class MemberController {
	
	private final MemberService memberService;
	
	/**
	 *	회원가입 
	 */
	@GetMapping("/member/signUp")
	public String SignUpForm() {
		return "user/signUpForm";
	}
	
	@PostMapping("/signUp")
	public String memberSignUp(@ModelAttribute("memberJoinForm") MemberVO memberVO) {
		
		System.out.println(memberVO);
		memberService.defaultSignUp(memberVO);
		
		return "redirect:/";
	}
	
	
//	  @PostMapping("/login") 
//	  public @ResponseBody String member(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
//	  
//		  System.out.println("customUserDetails :" + customUserDetails.getMember());
//		  
//		  return "home";
//	  
//	  }
	 
}
