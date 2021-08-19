package my.vono.web.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import org.springframework.web.bind.annotation.ModelAttribute;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;
import my.vono.web.gspeech.InfiniteStreamRecognize;
import my.vono.web.model.meeting.MeetingDto;
import my.vono.web.model.response.DefaultResponseDto;
import my.vono.web.service.MeetingService;
import my.vono.web.service.WasteBasketService;

@Controller
@RequiredArgsConstructor
public class MeetingController {

	private final MeetingService meetingService;
	private final WasteBasketService wasteBasketService;

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
			.addAttribute("content", content);
		System.out.println(model);
		return "meeting/startMeeting";
	}

//		return "meeting/startMeeting";
//	}


	



	@RequestMapping("startRecording")
	public String startRecording(HttpServletRequest request, HttpServletResponse response, Model model) {
		System.out.println("startRecording호출");
		InfiniteStreamRecognize.StreamStart(model,"");
		//받고 비동기처리 출력까지 이어져야함
		return "meeting/startMeeting";
	}

	@RequestMapping("endMeeting")
	public String endMeeting() {
		System.out.println("endMeeting호출");
		return "meeting/startMeeting";
	}
	
	
	

//	@GetMapping("/meeting/insert")
//	public String meetingInsert(){
//     return "";	

//}

   // 회의록 목록
//   @GetMapping("/meetings")
//   public String meetings(){
//    return "";   
//}

   // 회의록 작성
   @ResponseBody
   @PostMapping("/meeting/write")
   public ResponseEntity<?> meetingWrite(@RequestBody MeetingDto meetingDto) {
      try {
         meetingService.createMeeting(meetingDto);
         return new ResponseEntity<>(new DefaultResponseDto<>(true, "회의록 생성에 성공하였습니다.", null), HttpStatus.CREATED);
      } catch (Exception e) {
         return new ResponseEntity<>(new DefaultResponseDto<>(false, "회의록 생성에 실패하였습니다.", null), HttpStatus.OK);
      }
   }

   
   //회의록 휴지통
   @ResponseBody
   @PostMapping("/meeting/trash")
   public ResponseEntity<?> trashMeeting(@RequestBody MeetingDto meetingDto) {
      try {
         meetingService.deleteMeeting(meetingDto.getId());
         return new ResponseEntity<>(new DefaultResponseDto<>(true, "회의록을 삭제 하였습니다.", null), HttpStatus.OK);
      } catch (Exception e) {
         return new ResponseEntity<>(new DefaultResponseDto<>(false, "회의록 삭제에 실패하였습니다.", null), HttpStatus.OK);
      }
   }
   //회의록 조회
   @ResponseBody
   @GetMapping("/meeting")
   public ResponseEntity<?>detailMeeting(@RequestParam("meetingId") Long meetingId){
      try {
         
         return new ResponseEntity<>(new DefaultResponseDto<>(true, "회의록 조회 성공", meetingService.detailMeeting(meetingId)), HttpStatus.OK);
      } catch (Exception e) {
         return new ResponseEntity<>(new DefaultResponseDto<>(false, "회의록이 존재 하지 않습니다.", null), HttpStatus.OK);
      }
   }
   
   
   @ResponseBody
   @PostMapping("/meeting/move")
   public ResponseEntity<?>MoveMeeting(@RequestBody MeetingDto meetingDto){
      try {
         System.out.println(meetingDto.getFolderName());
         meetingService.moveMeeting(meetingDto.getFolderName(), meetingDto.getId());
         return new ResponseEntity<>(new DefaultResponseDto<>(true, "회의록 이동 성공", null), HttpStatus.OK);
      } catch (Exception e) {
         return new ResponseEntity<>(new DefaultResponseDto<>(false, "회의록 이동 실패", null), HttpStatus.OK);
      }
   }
   
//======================================   
   
   //휴지통 폴더 목록 조회
   @ResponseBody
   @GetMapping("/trash")
   public ResponseEntity<?>trash(@RequestParam("id")Long id){
      try {
         
         return new ResponseEntity<>(new DefaultResponseDto<>(true, "휴지통 조회 성공", wasteBasketService.findWasteBasket(id)), HttpStatus.OK);
      } catch (Exception e) {
         e.printStackTrace();
         return new ResponseEntity<>(new DefaultResponseDto<>(false, "휴지통 조회 실패", null), HttpStatus.OK);
      }
   }
   
   //폴더 복구
   @ResponseBody
   @PostMapping("/folder/recover")
   public ResponseEntity<?>recoverFolder(@RequestParam("id") List<Long> id){
      try {
         wasteBasketService.recoverFolder(id);
         return new ResponseEntity<>(new DefaultResponseDto<>(true, "폴더 복구 성공", null), HttpStatus.OK);
      } catch (Exception e) {
         return new ResponseEntity<>(new DefaultResponseDto<>(false, "폴더 복구 실패", null), HttpStatus.OK);
      }
   }
   //회의록 복구
   @ResponseBody
   @PostMapping("/meeting/recover")
   public ResponseEntity<?>recoverMeeting(@RequestParam("id") List<Long> id){
      try {
         wasteBasketService.recoverMeeting(id);
         return new ResponseEntity<>(new DefaultResponseDto<>(true, "회의록 복구 성공", null), HttpStatus.OK);
      } catch (Exception e) {
         return new ResponseEntity<>(new DefaultResponseDto<>(false, "회의록 복구 실패", null), HttpStatus.OK);
      }
   }
   //폴더 영구삭제
   @ResponseBody
   @PostMapping("/folder/delete")
   public ResponseEntity<?>deleteFolder(@RequestParam("id") List<Long> id){
      try {
         wasteBasketService.permanentlyDeleteFolder(id);
         return new ResponseEntity<>(new DefaultResponseDto<>(true, "폴더 영구 삭제 성공", null), HttpStatus.OK);
      } catch (Exception e) {
         e.printStackTrace();
         return new ResponseEntity<>(new DefaultResponseDto<>(false, "폴더 영구 삭제 실패", null), HttpStatus.OK);
      }
   }
   
   @ResponseBody
   @PostMapping("/meeting/delete")
   public ResponseEntity<?>deleteMeeting(@RequestParam("id") List<Long> id){
      try {
         wasteBasketService.permanentlyDeleteFolder(id);
         return new ResponseEntity<>(new DefaultResponseDto<>(true, "회의록 영구 삭제 성공", null), HttpStatus.OK);
      } catch (Exception e) {
         return new ResponseEntity<>(new DefaultResponseDto<>(false, "회의록 영구 삭제 실패", null), HttpStatus.OK);
      }
   }
   
   
      
   }
   