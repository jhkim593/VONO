package my.vono.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import my.vono.web.service.MemberService;

@Controller
@RequiredArgsConstructor
public class MemberController {
	
	private final MemberService memberService;
	
//	@PostMapping("signUpForm")
//	public String memberSignUp() {
//		return "signUpForm";
//	}
	

	
//	@PostMapping("/member/login")
//	public String memberLogin() {
//		
//		return "ok"
//	} 
	
//	@PostMapping("/member/signUp")
//	public String memberSignUp() {
//		
//		return 
//	} 
//	@PostMapping("/member/logout")
//	public String memberLogout(HttpServletRequest request,MemberVO vo) {
//		
//		HttpSession session=request.getSession();
//		session.setAttribute(String.valueOf(vo.getId()),vo);
//		
//	}
//	@PostMapping("/member/logout")
//	public String memberLogout(HttpServletRequest request,FolderVO vo) {
//		
//		HttpSession session=request.getSession();
//		MemberVO memvo=(MemberVO)session.getAttribute(String.valueOf(vo.getId()));
//		memvo.getId();
//		
//		return
//	}
	
}
