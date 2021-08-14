package my.vono.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.JsonObject;

import lombok.RequiredArgsConstructor;
import my.vono.web.entity.Meeting;
import my.vono.web.gspeech.InfiniteStreamRecognize;
import my.vono.web.model.meeting.MeetingDto;
import my.vono.web.service.MeetingService;
@Controller
@RequiredArgsConstructor
public class MeetingController {
	
	private final MeetingService meetingService;
	
	@RequestMapping("newMeeting")
	public String newMeeting(){
		
		System.out.println("newMeeting호출");
		
		return "meeting/newMeeting";
	}
	
	@RequestMapping("startMeeting")
	public String startMeeting(HttpServletRequest request, Model model) {
		System.out.println("startMeeting호출");
		
		//데이터 처리 , 여기서 thymeleaf를 이용하여 startMeeting.html을 연결할 수 있는가?
		//가능하다면 1,2 합치기 https://sidepower.tistory.com/145
		//jquery 버리고 다른방법 찾기 https://www.leafcats.com/28
		//https://chaelin1211.github.io/study/2021/04/14/thymeleaf-ajax.html 
		String name = request.getParameter("mt_name");
		String date = request.getParameter("mt_date");
		String participant = request.getParameter("mt_participant");
		String content = request.getParameter("mt_content");
		//Meeting.createMeeting(name, content, participant, null);
		model.addAttribute("name", name)  //-> th:text="${name}" 로 사용
			.addAttribute("date", date)
			.addAttribute("participant", participant)
			.addAttribute("content", content)
			.addAttribute("test", "testValue");
		System.out.println(model);
		return "meeting/startMeeting";
		//return new ModelAndView("startMeeting2"); //페이지 이동
	}
//	@RequestMapping("startMeeting2")
//	public void startMeeting2(HttpServletRequest request, HttpServletResponse response, Model model) {
//		System.out.println("startMeeting2호출");
//		//데이터 처리 , 여기서 thymeleaf를 이용하여 startMeeting.html을 연결할 수 있는가?
//		//가능하다면 1,2 합치기 https://sidepower.tistory.com/145
//		String name = request.getParameter("name");
//		String date = request.getParameter("date");
//		String participant = request.getParameter("participant");
//		String content = request.getParameter("content");
//		String reference = request.getParameter("reference");
//		Meeting.createMeeting(name, content, participant, null);
//		model.addAttribute("name", name)  //-> th:text="${name}" 로 사용
//			.addAttribute("date", date)
//			.addAttribute("participant", participant)
//			.addAttribute("content", content)
//			.addAttribute("reference", reference);
//		System.out.println(model);
//		
//	}
	
	@RequestMapping("startRecording")
	public String startRecording(HttpServletRequest request, HttpServletResponse response, Model model) {
		System.out.println("startRecording호출");
		InfiniteStreamRecognize.StreamStart("");
		//받고 비동기처리 출력까지 이어져야함
		return null;
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
