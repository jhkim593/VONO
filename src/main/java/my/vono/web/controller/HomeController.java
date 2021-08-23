package my.vono.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class HomeController {
	
	@GetMapping("/")
	public String home(@RequestParam(required = false) boolean error, Model m) {
		
		m.addAttribute("error", error);
		
		return "home";
	}
	
	

}
