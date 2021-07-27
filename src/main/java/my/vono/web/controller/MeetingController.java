package my.vono.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.RequiredArgsConstructor;
import my.vono.web.service.MeetingService;
@Controller
@RequiredArgsConstructor
public class MeetingController {
	
	
	private final MeetingService meetingService;
	
	//표지
//	@GetMapping("/meeting/insert")
//	public String meetingInsert(){
//     return "";	
//}
	
	//회의록 목록
//	@GetMapping("/meetings")
//	public String meetings(){
//    return "";	
//}

	//회의록 작성
//	@GetMapping("/meeting/write")
//	public String meetingWrite(){    
//	return "";	
	//}
	
}
