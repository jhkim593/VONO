package my.vono.web.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.RequiredArgsConstructor;
import my.vono.web.config.auth.CustomUserDetails;
import my.vono.web.excelUtile.MeetingLogVO;
import my.vono.web.gspeech.InfiniteStreamRecognize;
import my.vono.web.model.meeting.MeetingDetailDto;
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
	public String newMeeting() {
		System.out.println("newMeeting호출");
		return "meeting/newMeeting";
	}

	@RequestMapping("startMeeting")
	public String startMeeting(HttpServletRequest request, Model model) {
		System.out.println("startMeeting호출");
		String name = request.getParameter("mt_name");
		String date = request.getParameter("mt_date");
		String participant = request.getParameter("mt_participant");
		String content = request.getParameter("mt_content");
		model.addAttribute("name", name)
				.addAttribute("date", date).addAttribute("participant", participant).addAttribute("content", content);
		System.out.println(model);
		return "meeting/startMeeting";
	}

	@ResponseBody
	@PostMapping("startRecording")
	public String startRecording(HttpServletRequest request, HttpServletResponse response, Model model) {
		System.out.println("startRecording호출");
		InfiniteStreamRecognize.StreamStart(model,"");

		return "Recording";//녹음중=Recording


	}
	
	@ResponseBody
	@GetMapping("infiniteReq")
	public MeetingLogVO infiniteReq() {
		//System.out.println("infiniteReq호출");
		if (InfiniteStreamRecognize.result != null) {
			if (InfiniteStreamRecognize.result.getIsFinal()) {
				String str = InfiniteStreamRecognize.res;
				String[] strlist = str.split("/");
				MeetingLogVO meetingLogVO = new MeetingLogVO();
				meetingLogVO.setTime(strlist[0]);
				meetingLogVO.setSpeaker(strlist[1]);
				meetingLogVO.setContent(strlist[2]);
				return meetingLogVO;
				
			} else {
				return null;
			}
		}
		return null;
	}

	@ResponseBody
	@RequestMapping("pauseRecording")
	public String pauseRecording(Model model) {
		System.out.println("pauseMeeting호출");
		InfiniteStreamRecognize.StreamPause(model,"");
		return "녹화중지";
	}
	
	@ResponseBody
	@RequestMapping("restartRecording")
	public String restartRecording(Model model) {
		System.out.println("restartMeeting호출");
		InfiniteStreamRecognize.StreamRestart(model,"");
		return "녹화재시작";
	}
	
	@RequestMapping("endRecording")
	public String endRecording(Model model, @RequestParam ("inputHidden") String inputHidden, 
			 @RequestParam ("mt_name") String mt_name, 
			 @RequestParam ("mt_date") String mt_date, 
			 @RequestParam ("mt_participant") String mt_participant, 
			 @RequestParam ("mt_content") String mt_content,
			 @AuthenticationPrincipal CustomUserDetails custom) {
		System.out.println(inputHidden);
		MeetingDto meetingDto = new MeetingDto();
		meetingDto.setName(mt_name);
		meetingDto.setParticipant(mt_participant);
		meetingDto.setContent(mt_content);
		
		JSONParser jsonParser = new JSONParser();
	    // JSON데이터를 넣어 JSON Object 로 만들어 준다.
	    JSONObject jsonObject;
	    try {
	    	jsonObject = (JSONObject) jsonParser.parse(inputHidden);

	    	JSONArray array = (JSONArray) jsonObject.get("list");
	    	List<String>memoStr=new ArrayList<>();

	    	for (int i = 0; i < array.size(); i++) {
	        	// JSONArray 형태의 값을 가져와 JSONObject 로 풀어준다.
	        	JSONObject obj = (JSONObject) array.get(i);
	                  
	            memoStr.add(String.valueOf(obj.get("memo")));
	    	}
	    	
	    	String filename= InfiniteStreamRecognize.StreamEnd(model,memoStr,"");
	    	meetingDto.setRecToTextUrl(filename);
	    	meetingService.createMeeting(meetingDto, custom.getMember().getId());
	         
	    } catch (Exception e) {
	    	// TODO Auto-generated catch block
	    	e.printStackTrace();
	    	return "meeting/newMeeting"; //목적지 바꾸기
	    }


	      return "meeting/newMeeting";

	}

   // 회의록 작성
   @ResponseBody
   @PostMapping("/meeting/write")
   public ResponseEntity<?> meetingWrite(@RequestBody MeetingDto meetingDto ,@AuthenticationPrincipal CustomUserDetails custom) {
      try {
         meetingService.createMeeting(meetingDto ,custom.getMember().getId());
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
//   //회의록 조회
//   @ResponseBody
//   @GetMapping("/meeting")
//   public ResponseEntity<?>detailMeeting(@RequestParam("meetingId") Long meetingId){
//      try {
//         
//         return new ResponseEntity<>(new DefaultResponseDto<>(true, "회의록 조회 성공", meetingService.detailMeeting(meetingId)), HttpStatus.OK);
//      } catch (Exception e) {
//         return new ResponseEntity<>(new DefaultResponseDto<>(false, "회의록이 존재 하지 않습니다.", null), HttpStatus.OK);
//      }
//   }
   
   
   @ResponseBody
   @PostMapping("/meeting/move")
   public ResponseEntity<?>MoveMeeting(@RequestBody MeetingDto meetingDto ,@AuthenticationPrincipal CustomUserDetails custom){
      try {
         System.out.println(meetingDto.getFolderName());
         meetingService.moveMeeting(meetingDto.getFolderName(), meetingDto.getId(),custom);
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
   
      


	@GetMapping("meeting")
	public String detailMeeting(
			@RequestParam("id")Long meetingId, 
			Model model) {
		try {
			
			MeetingDto meetingDto=meetingService.detailMeeting(meetingId);
			System.out.println("============================="+meetingDto.getRecToTextUrl());
			System.out.println("============================="+meetingDto.getRecToTextUrl());
			MeetingDetailDto meetLog = meetingService.meetingReader(meetingDto.getRecToTextUrl());
//   
			model.addAttribute("meetingLog", meetLog.getMList());
			model.addAttribute("memo", meetLog.getMemo());
			System.out.println(meetLog.getMemo());
			model.addAttribute("meeting",meetingDto);
			

		} catch (Exception e) {
			e.printStackTrace();
		}
		return "meeting/detailMeeting";
	}

	@ResponseBody
	@PostMapping("meeting/update")
	public String updateMeeting(@RequestParam String list,
			@RequestParam String memo,@RequestParam("meetingId")Long meetingId ) {
		System.out.println("==============");
		System.out.println(memo);

		JSONParser jsonParser = new JSONParser();
		String url=meetingService.detailMeeting(meetingId).getRecToTextUrl();
		

		// JSON데이터를 넣어 JSON Object 로 만들어 준다.
		JSONObject jsonObject;
		try {
			jsonObject = (JSONObject) jsonParser.parse(list);

			JSONArray array = (JSONArray) jsonObject.get("list");
			List<MeetingLogVO>meetingLogVOs=new ArrayList<>();

			for (int i = 0; i < array.size(); i++) {
				MeetingLogVO m=new MeetingLogVO();

				// JSONArray 형태의 값을 가져와 JSONObject 로 풀어준다.
				JSONObject obj = (JSONObject) array.get(i);
						
				m.setSpeaker(String.valueOf(obj.get("speaker")));
				m.setContent(String.valueOf(obj.get("content")));
				m.setTime(String.valueOf(obj.get("time")));
			
				meetingLogVOs.add(m);

			}
			meetingService.meetingWrite(meetingLogVOs, url);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "수정에 실패 하였습니다.";
		}

		return "수정에 성공 하였습니다.";
	}
	

    @RequestMapping("getmtingLogList")
	public String getAllFolders(Model m,@RequestParam(required = false, defaultValue = "") Long id) {
    	m.addAttribute("listName", meetingService.folderMeetingList(id));
		return "folder/meetingList";
	}


    @RequestMapping("istrashfile")
	public String istrashfile(@ModelAttribute("getfolderID") Long id) {
    	meetingService.deleteMeeting(id);
		return "folder/meetingList";
	}
    @RequestMapping("movefile")
   	public String movefile(@RequestParam(required = false , value = "id") Long folderID ,@RequestParam(required = false, value = "meetingID") Long id ) {
       	meetingService.moveMeeting1(id, folderID);
   		return "folder/folderList";
   	}
}
