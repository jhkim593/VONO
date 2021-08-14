package my.vono.web.controller;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;
import my.vono.web.service.MemberService;

@Controller
@RequiredArgsConstructor
public class HomeController {
	
	@GetMapping("/")
	public String home(@RequestParam(required = false) boolean error, Model m) {
		
		m.addAttribute("error", error);
		
		return "home";
	}
	
	

}
