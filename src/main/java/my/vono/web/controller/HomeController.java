package my.vono.web.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;
import my.vono.web.config.auth.CustomUserDetails;

@Controller
@RequiredArgsConstructor
public class HomeController {
	
	@GetMapping("/")
	public String home(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
		System.out.println("=============asdas"); 
		if(customUserDetails!=null) {
			 return "meeting/newMeeting";
		 }
		
		
		return "home";
	}
	
	

}
