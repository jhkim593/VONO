package my.vono.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;
import my.vono.web.gspeech.InfiniteStreamRecognize;
import my.vono.web.service.MeetingService;
@Controller
@RequiredArgsConstructor
public class MeetingController {
	
	
	private final MeetingService meetingService;
	
	
	@RequestMapping("newMeeting")
	public String newMeeting(){
		System.out.println("newMeeting호출");
		return "newMeeting";
	}
	
	@ResponseBody
	@RequestMapping("startMeeting")
	public String startMeeting(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("startMeeting호출, 본격적인 녹음 시작");
		String test = request.getParameter("meeting_name");
		System.out.println(test);
		//녹화 핵심기능, 근데 키면 녹음되면서 다음걸로 안넘어가짐
		//InfiniteStreamRecognize.StreamStart("");
		
		//화면 넘어가서 녹음시작으로 바꿔보자
		return "startMeeting";
	}
	
	@RequestMapping("endMeeting")
	public void endMeeting() {
		System.out.println("endMeeting호출");
		InfiniteStreamRecognize.StreamStart(null);
//		return "startMeeting";
	}
	
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
