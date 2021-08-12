package my.vono.web.controller;

import javax.validation.Valid;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import my.vono.web.entity.Member;
import my.vono.web.model.user.MemberDAO;
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
	
//	@PostMapping("/member/login")
//	public String memberLogin() {
//		
//		return "ok"
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
